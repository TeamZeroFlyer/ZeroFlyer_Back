package zeroflyer.qrecode.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.main.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    @Value("${jwt.refresh-cookie-key}")
    private String cookieKey;

    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
                .map(Cookie::getValue).orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_REFRESH_TOKEN_COOKIE));

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new PrivateException(StatusCode.NOT_VALIDATED_REFRESH_TOKEN);
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldRefreshToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Long id = Long.valueOf(user.getName());

        // 3. Match Refresh Token

        String savedToken = memberRepository.findByIdx(id)
                .orElseThrow(
                        ()->new PrivateException(StatusCode.NOT_FOUND_MEMBER)
                ).getRefreshToken();

        if (!savedToken.equals(oldRefreshToken)) {
            throw new PrivateException(StatusCode.NOT_MATCHED_REFRESH_TOKEN);
        }

        // 4. JWT 갱신
        String accessToken = tokenProvider.createAccessToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        Map<String, Object> ob = new HashMap<>();
        ob.put("accessToken", accessToken);

        return ob;
    }
}

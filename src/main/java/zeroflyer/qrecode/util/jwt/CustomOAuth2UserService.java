package zeroflyer.qrecode.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import zeroflyer.qrecode.dto.auth.OAuthAttributeDto;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.main.domain.AuthProvider;
import zeroflyer.qrecode.main.domain.Grade;
import zeroflyer.qrecode.main.domain.Member;
import zeroflyer.qrecode.main.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    // OAuth2UserRequest에 있는 Access Token으로 유저정보 get
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        return process(oAuth2UserRequest, oAuth2User);
    }

    // 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributeDto attributes = OAuthAttributeDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        if (attributes.getEmail().isEmpty()) {
            throw new PrivateException(StatusCode.NOT_FOUND_MEMBER);
        }
        Optional<Member> userOptional = memberRepository.findByMemberId(attributes.getEmail());
        Member user;

        if (userOptional.isPresent()) {        // 이미 가입된 경우
            user = userOptional.get();
            if (authProvider != user.getAuthProvider()) {
                throw new PrivateException(StatusCode.WRONG_SOCIAL_LOGIN_TYPE);
            }

        } else {
            user = createUser(attributes, authProvider);
        }
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private Member createUser(OAuthAttributeDto userInfo, AuthProvider authProvider) {
        Member user = Member.builder()
                .memberName(userInfo.getName())
                .memberId(userInfo.getEmail())
                .authProvider(authProvider)
                .grade(Grade.USER)
                .build();
        return memberRepository.save(user);
    }
}

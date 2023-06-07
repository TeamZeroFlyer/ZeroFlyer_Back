package zeroflyer.qrecode.dto.Auth;

import lombok.Builder;
import lombok.Getter;
import zeroflyer.qrecode.main.domain.Member;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributeDto {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OAuthAttributeDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributeDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributeDto.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributeDto ofKakao(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) attributes.get("properties");

        return OAuthAttributeDto.builder()
                .name((String) account.get("nickname"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey("id")
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(name)
                .memberId(email)
                .build();
    }

}

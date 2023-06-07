package zeroflyer.qrecode.dto.Auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCheckMemberDto {
    private String accessToken;
}

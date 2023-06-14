package zeroflyer.qrecode.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseUserInfoDto {
    private String userName;
    private Long userPoint;
    private Long userScanCount;

    @Builder
    public ResponseUserInfoDto(String userName, Long userPoint, Long userScanCount) {
        this.userName = userName;
        this.userPoint = userPoint;
        this.userScanCount = userScanCount;
    }
}

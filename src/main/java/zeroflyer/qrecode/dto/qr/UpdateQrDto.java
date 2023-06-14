package zeroflyer.qrecode.dto.qr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateQrDto {
    private Long qrFlyerIdx;

    private String qrPtjName;

    private String qrPtjPhone;
}

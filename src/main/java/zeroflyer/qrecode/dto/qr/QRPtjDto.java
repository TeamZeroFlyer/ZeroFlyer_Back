package zeroflyer.qrecode.dto.qr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QRPtjDto {
    private String ptjName;
    private String ptjPhone;
}

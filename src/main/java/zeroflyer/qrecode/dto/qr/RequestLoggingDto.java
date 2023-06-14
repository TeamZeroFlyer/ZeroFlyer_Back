package zeroflyer.qrecode.dto.qr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestLoggingDto {

    private Long storeIdx;

    private Long flyerIdx;

    private Long qrIdx;
}

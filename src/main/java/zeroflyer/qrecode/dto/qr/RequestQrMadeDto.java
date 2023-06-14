package zeroflyer.qrecode.dto.qr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestQrMadeDto {
    private Long qrFlyerIdx;
    private List<QRPtjDto> qrPtj;
}

package zeroflyer.qrecode.dto.qr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zeroflyer.qrecode.main.domain.QR;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseQrInfoDto {
    private Long idx;

    private String qrNum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp qrStart;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp qrEnd;

    private String ptjName;

    private String ptjPhone;

    private Long flyerIdx;

    private String flyerName;

    private Long scanCount;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp timeStamp;

    @Builder
    public ResponseQrInfoDto(QR qr, String flyerName, Long scanCount) {
        this.idx = qr.getIdx();
        this.qrNum = qr.getQrNum();
        this.qrStart = qr.getQrStart();
        this.qrEnd = qr.getQrEnd();
        this.ptjName = qr.getQrPtjName();
        this.ptjPhone = qr.getQrPtjPhone();
        this.flyerIdx = qr.getQrFlyerIdx();
        this.timeStamp = qr.getQrTimeStamp();
        this.flyerName = flyerName;
        this.scanCount = scanCount;
    }
}

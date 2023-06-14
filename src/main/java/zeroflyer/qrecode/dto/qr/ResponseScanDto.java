package zeroflyer.qrecode.dto.qr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseScanDto {
    private Long storeIdx;

    private String storeName;

    private Long qrScanCount;

    private String qrNum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp qrTimestamp;

    private Long flyerIdx;

    private String flyerUrl;

    @Builder
    public ResponseScanDto(Long storeIdx, String storeName, Long qrScanCount, String qrNum, Timestamp qrTimestamp, Long flyerIdx, String flyerUrl) {
        this.storeIdx = storeIdx;
        this.storeName = storeName;
        this.qrScanCount = qrScanCount;
        this.qrNum = qrNum;
        this.qrTimestamp = qrTimestamp;
        this.flyerIdx = flyerIdx;
        this.flyerUrl = flyerUrl;
    }
}

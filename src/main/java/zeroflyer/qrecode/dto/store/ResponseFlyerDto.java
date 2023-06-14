package zeroflyer.qrecode.dto.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import zeroflyer.qrecode.main.domain.Flyer;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseFlyerDto {
    private Long idx;

    private String flyerUrl;

    private String flyerName;

    private String flyerTag;

    private Long flyerQrCount;

    private Long flyerScanCount;

    @JsonFormat(pattern = "yyyyMMdd")
    private Timestamp flyerStart;

    @JsonFormat(pattern = "yyyyMMdd")
    private Timestamp flyerEnd;

    private Boolean hasCoupon;


    @Builder
    public ResponseFlyerDto(Flyer flyer, Long qrCount, Long scanCount) {
        this.idx = flyer.getIdx();
        this.flyerUrl = flyer.getFlyerUrl();
        this.flyerName = flyer.getFlyerName();
        this.flyerTag = flyer.getFlyerTag();
        this.flyerQrCount = qrCount;
        this.flyerScanCount = scanCount;
        this.flyerStart = flyer.getFlyerStart();
        this.flyerEnd = flyer.getFlyerEnd();
        this.hasCoupon = flyer.getHasCoupon();
    }
}

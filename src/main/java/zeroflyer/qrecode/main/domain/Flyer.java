package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;
import zeroflyer.qrecode.dto.store.RequestFlyerInfoDto;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String flyerUrl;

    @Column(nullable = false)
    private String flyerName;

    @Column(columnDefinition = "TEXT")
    private String flyerTag;

    @ColumnDefault("0")
    private Long flyerQrCount;

    @Column(nullable = false)
    private Long flyerStoreIdx;

    @ColumnDefault("0")
    private Long flyerScanCount;

    private Timestamp flyerStart;

    private Timestamp flyerEnd;

    private Boolean hasCoupon;

    @ColumnDefault("true")
    private Boolean isValid;

    @Builder
    public Flyer(RequestFlyerInfoDto dto, Long idx) {
        this.flyerUrl = dto.getFlyerUrl();
        this.flyerName = dto.getFlyerName();
        this.flyerTag = dto.getFlyerTag();
        this.flyerStart = dto.getFlyerStart();
        this.flyerEnd = dto.getFlyerEnd();
        this.flyerStoreIdx = idx;
        this.hasCoupon = dto.getHasCoupon();
    }

    public void setUpdate(RequestFlyerInfoDto dto) {
        if (dto.getFlyerUrl() != null) {
            this.flyerUrl = dto.getFlyerUrl();
        }
        if (dto.getFlyerName() != null) {
            this.flyerName = dto.getFlyerName();
        }
        if (dto.getFlyerTag() != null) {
            this.flyerTag = dto.getFlyerTag();
        }
        if (dto.getFlyerEnd() != null) {
            this.flyerEnd = dto.getFlyerEnd();
        }
        if (dto.getFlyerStart() != null) {
            this.flyerStart = dto.getFlyerStart();
        }
        if (dto.getHasCoupon() != null) {
            this.hasCoupon = dto.getHasCoupon();
        }
    }

    public void delete() {
        this.isValid = false;
    }
}

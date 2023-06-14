package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import zeroflyer.qrecode.dto.qr.QRPtjDto;
import zeroflyer.qrecode.dto.qr.RequestQrMadeDto;
import zeroflyer.qrecode.dto.qr.UpdateQrDto;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String qrNum;

    private Long qrStoreIdx;

    private Timestamp qrStart;

    private Timestamp qrEnd;

    private String qrPtjName;

    private String qrPtjPhone;

    private Long qrFlyerIdx;

    @CreationTimestamp
    private Timestamp qrTimeStamp;

    @Builder
    public QR(Long qrStoreIdx, String qrNum, Timestamp qrStart, Timestamp qrEnd, QRPtjDto dto, Long qrFlyerIdx) {
        this.qrStoreIdx = qrStoreIdx;
        this.qrNum = qrNum;
        this.qrStart = qrStart;
        this.qrEnd = qrEnd;
        this.qrPtjName = dto.getPtjName();
        this.qrPtjPhone = dto.getPtjPhone();
        this.qrFlyerIdx = qrFlyerIdx;
    }

    public void updateQr(UpdateQrDto dto, Timestamp qrStart, Timestamp qrEnd) {
        this.qrStart = qrStart;
        this.qrEnd = qrEnd;
        this.qrPtjName = dto.getQrPtjName();
        this.qrPtjPhone = dto.getQrPtjPhone();
        this.qrFlyerIdx = dto.getQrFlyerIdx();
    }
}

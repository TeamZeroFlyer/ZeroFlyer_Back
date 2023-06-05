package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
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

    @ColumnDefault("0")
    private Long qrScanCount;

    private String qrStart;

    private String qrEnd;

    private String qrPtjName;

    private String qrPtjPhone;

    private Long qrFlyerIdx;

    @CreationTimestamp
    private Timestamp qrTimeStamp;
}

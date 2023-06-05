package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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
    private String flyerStoreName;

    @Column(nullable = false)
    private String flyerStoreAddress;

    @Column(nullable = false)
    private String flyerStorePhone;

    private String flyerStoreStart;

    private String flyerStoreEnd;

    @Column(nullable = false)
    private Long flyerStoreIdx;

    @ColumnDefault("0")
    private Long flyerScanCount;
}

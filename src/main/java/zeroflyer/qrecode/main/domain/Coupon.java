package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String couponNum;

    private String couponName;

    private String couponInfo;

    private Long couponStoreIdx;

    private Long couponFlyerIdx;
}

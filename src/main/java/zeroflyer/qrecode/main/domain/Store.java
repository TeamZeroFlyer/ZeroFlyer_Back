package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String storeNum;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storePhone;

    @Column(nullable = false)
    private String storeAddress;

    private String storeStart;

    private String storeEnd;

    private String storeLat;

    private String storeLong;

    @Column(columnDefinition = "TEXT")
    private String storeTag;
}

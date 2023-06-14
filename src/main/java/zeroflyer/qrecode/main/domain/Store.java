package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zeroflyer.qrecode.dto.store.RequestFlyerInfoDto;
import zeroflyer.qrecode.dto.store.RequestStoreInfoDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storePhone;

    @Column(nullable = false)
    private String storeAddress;

    private String detailAddress;

    private String storeStart;

    private String storeEnd;

    private String storeLat;

    private String storeLng;

    private Long memberIdx;

    @Column(columnDefinition = "TEXT")
    private String storeDescription;

    @Column(columnDefinition = "TEXT")
    private String storeTag;

    @Builder
    public Store(RequestStoreInfoDto dto, String lat, String lng, Long memberIdx) {
        this.memberIdx = memberIdx;
        this.storeName = dto.getStoreName();
        this.storePhone = dto.getStorePhone();
        this.storeAddress = dto.getStoreAddress();
        this.detailAddress = dto.getDetailAddress();
        this.storeStart = dto.getStoreStart();
        this.storeEnd = dto.getStoreEnd();
        this.storeLat = lat;
        this.storeLng = lng;
        this.storeDescription = dto.getStoreDescription();
        this.storeTag = dto.getStoreTag();
    }

    public void setUpdate(RequestStoreInfoDto dto, String lat, String lng) {
        if (dto.getStoreName() != null) {
            this.storeName = dto.getStoreName();
        }
        if (dto.getStorePhone() != null) {
            this.storePhone = dto.getStorePhone();
        }
        if (dto.getStoreAddress() != null) {
            this.storeAddress = dto.getStoreAddress();
        }
        if (dto.getDetailAddress() != null) {
            this.detailAddress = dto.getDetailAddress();
        }
        if (dto.getStoreDescription() != null) {
            this.storeDescription = dto.getStoreDescription();
        }
        if (dto.getStoreStart() != null) {
            this.storeStart = dto.getStoreStart();
        }
        if (dto.getStoreEnd() != null) {
            this.storeEnd = dto.getStoreEnd();
        }
        if (dto.getStoreTag() != null) {
            this.storeTag = dto.getStoreTag();
        }
        if (lat != null) {
            this.storeLat = lat;
        }
        if (lng != null) {
            this.storeLng = lng;
        }
    }
}

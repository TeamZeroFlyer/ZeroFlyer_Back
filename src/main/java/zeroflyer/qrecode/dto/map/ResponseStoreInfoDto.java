package zeroflyer.qrecode.dto.map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zeroflyer.qrecode.main.domain.Store;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStoreInfoDto {

    private String storeName;

    private String storePhone;

    private String storeAddress;

    private String storeStart;

    private String storeEnd;

    private String storeLat;

    private String storeLng;

    private String storeTag;

    private String storeDescription;

    private Boolean hasCoupon;

    public ResponseStoreInfoDto(Store store, Boolean hasCoupon) {
        this.storeName = store.getStoreName();
        this.storePhone = store.getStorePhone();
        this.storeAddress = store.getStoreAddress();
        this.storeStart = store.getStoreStart();
        this.storeEnd = store.getStoreEnd();
        this.storeLat = store.getStoreLat();
        this.storeLng = store.getStoreLng();
        this.storeTag = store.getStoreTag();
        this.storeDescription = store.getStoreDescription();
        this.hasCoupon = hasCoupon;
    }
}

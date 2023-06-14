package zeroflyer.qrecode.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zeroflyer.qrecode.main.domain.Store;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStoreDto {
    private String storeName;

    private String storePhone;

    private String storeAddress;

    private String detailAddress;

    private String storeDescription;

    private String storeStart;

    private String storeEnd;

    private String storeTag;

    @Builder
    public ResponseStoreDto(Store store) {
        this.storeName = store.getStoreName();
        this.storePhone = store.getStorePhone();
        this.storeAddress = store.getStoreAddress();
        this.detailAddress = store.getDetailAddress();
        this.storeDescription = store.getStoreDescription();
        this.storeStart = store.getStoreStart();
        this.storeEnd = store.getStoreEnd();
        this.storeTag = store.getStoreTag();
    }
}

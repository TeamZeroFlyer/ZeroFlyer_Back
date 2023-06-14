package zeroflyer.qrecode.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestStoreInfoDto {

    private String storeName;

    private String storePhone;

    private String storeAddress;

    private String detailAddress;

    private Float lat;

    private Float lng;

    private String storeDescription;

    private String storeStart;

    private String storeEnd;

    private String storeTag;

}

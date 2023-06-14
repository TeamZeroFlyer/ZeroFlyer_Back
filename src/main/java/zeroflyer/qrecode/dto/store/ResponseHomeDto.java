package zeroflyer.qrecode.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseHomeDto {
    private DailyChartDto yesterday;

    private DailyChartDto today;

    private Float percent;

    private Long first_week;
    private Long second_week;
    private Long third_week;

    private String storeName;
    private String storeUrl;
    private Long storeQrCount;
    private Long storeScanCount;
    private String userName;

    @Builder
    public ResponseHomeDto(DailyChartDto yesterday, DailyChartDto today, Float percent, Long first_week, Long second_week, Long third_week, String storeName, String storeUrl, Long storeQrCount, Long storeScanCount, String userName) {
        this.yesterday = yesterday;
        this.today = today;
        this.percent = percent;
        this.first_week = first_week;
        this.second_week = second_week;
        this.third_week = third_week;
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.storeQrCount = storeQrCount;
        this.storeScanCount = storeScanCount;
        this.userName = userName;
    }
}

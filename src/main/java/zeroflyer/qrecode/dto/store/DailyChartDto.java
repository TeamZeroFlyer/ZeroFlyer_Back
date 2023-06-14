package zeroflyer.qrecode.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DailyChartDto {
    private Long nine;
    private Long twelve;
    private Long fifteen;
    private Long eighteen;
    private Long twentyOne;

    @Builder
    public DailyChartDto(Long nine, Long twelve, Long fifteen, Long eighteen, Long twentyOne) {
        this.nine = nine;
        this.twelve = twelve;
        this.fifteen = fifteen;
        this.eighteen = eighteen;
        this.twentyOne = twentyOne;
    }
}

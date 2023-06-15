package zeroflyer.qrecode.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePointInfoDto {
    private List<PointLogDto> logList;
    private Long scanCount;
    private Long totalPoint;

    @Builder
    public ResponsePointInfoDto(List<PointLogDto> logList, Long scanCount, Long totalPoint) {
        this.logList = logList;
        this.scanCount = scanCount;
        this.totalPoint = totalPoint;
    }
}

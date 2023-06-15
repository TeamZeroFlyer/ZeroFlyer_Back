package zeroflyer.qrecode.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointLogDto {
    private String storeName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Timestamp timestamp;

    private Long point;

    @Builder
    public PointLogDto(String storeName, Timestamp timestamp, Long point) {
        this.storeName = storeName;
        this.timestamp = timestamp;
        this.point = point;
    }
}

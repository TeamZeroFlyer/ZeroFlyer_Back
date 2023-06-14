package zeroflyer.qrecode.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zeroflyer.qrecode.main.domain.Flyer;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseFlyerForUserDto {
    private Long idx;

    private String flyerUrl;

    private String flyerName;

    @JsonFormat(pattern = "yyyyMMdd")
    private Timestamp flyerStart;

    @JsonFormat(pattern = "yyyyMMdd")
    private Timestamp flyerEnd;

    private Boolean isValid;

    @Builder
    public ResponseFlyerForUserDto(Flyer flyer) {
        this.idx = flyer.getIdx();
        this.flyerUrl = flyer.getFlyerUrl();
        this.flyerName = flyer.getFlyerName();
        this.flyerStart = flyer.getFlyerStart();
        this.flyerEnd = flyer.getFlyerEnd();
        this.isValid = flyer.getIsValid();
    }
}

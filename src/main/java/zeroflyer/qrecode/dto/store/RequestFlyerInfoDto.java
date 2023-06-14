package zeroflyer.qrecode.dto.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestFlyerInfoDto {
    private String flyerUrl;
    private String flyerName;
    private String flyerTag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp flyerStart;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp flyerEnd;
    private Boolean hasCoupon;
}

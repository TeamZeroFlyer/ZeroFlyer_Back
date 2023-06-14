package zeroflyer.qrecode.dto.qr;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseQrListDto {
    private String date;

    private List<ResponseQrInfoDto> qrList;

    @Builder
    public ResponseQrListDto(String date, List<ResponseQrInfoDto> qrList) {
        this.date = date;
        this.qrList = qrList;
    }
}

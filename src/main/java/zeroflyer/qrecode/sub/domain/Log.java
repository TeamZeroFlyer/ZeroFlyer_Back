package zeroflyer.qrecode.sub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String memberId;

    private Long storeIdx;

    private Long flyerIdx;

    private Long qrIdx;

    @CreationTimestamp
    private Timestamp timestamp;

    @Builder
    public Log(String memberId, Long storeIdx, Long flyerIdx, Long qrIdx) {
        this.memberId = memberId;
        this.storeIdx = storeIdx;
        this.flyerIdx = flyerIdx;
        this.qrIdx = qrIdx;
    }
}

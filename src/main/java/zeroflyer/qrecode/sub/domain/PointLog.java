package zeroflyer.qrecode.sub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String memberId;

    private Long point;

    private Long storeIdx;

    @CreationTimestamp
    private Timestamp timestamp;

    @Builder
    public PointLog(String memberId, Long point, Long storeIdx) {
        this.memberId = memberId;
        this.point = point;
        this.storeIdx = storeIdx;
    }
}

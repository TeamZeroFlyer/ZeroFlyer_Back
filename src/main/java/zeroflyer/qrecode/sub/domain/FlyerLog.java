package zeroflyer.qrecode.sub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlyerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String memberId;

    private Long flyerIdx;

    @CreationTimestamp
    private Timestamp timestamp;

    @Builder
    public FlyerLog(String memberId, Long flyerIdx) {
        this.memberId = memberId;
        this.flyerIdx = flyerIdx;
    }
}

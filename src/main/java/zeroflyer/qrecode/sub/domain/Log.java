package zeroflyer.qrecode.sub.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    private String storeNum;

    private String flyerUrl;

    private String qrNum;

    @CreationTimestamp
    private Timestamp timestamp;
}

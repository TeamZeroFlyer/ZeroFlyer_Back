package zeroflyer.qrecode.main.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberPhone;

    @Column(nullable = false)
    private String memberName;

    private Long memberPoint;

    @ColumnDefault("0")
    private Long memberScanCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    private Long memberStoreIdx;
}

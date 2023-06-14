package zeroflyer.qrecode.main.domain;

import lombok.*;
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
    private String memberName;

    @ColumnDefault("0")
    private Long memberPoint;

    @ColumnDefault("0")
    private Long memberScanCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String refreshToken;

    @Builder
    public Member(String memberId, String memberName, AuthProvider authProvider, Grade grade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.authProvider = authProvider;
        this.grade = grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}

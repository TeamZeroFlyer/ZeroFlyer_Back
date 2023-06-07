package zeroflyer.qrecode.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zeroflyer.qrecode.main.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdx(Long idx);
    Optional<Member> findByMemberId(String memberId);
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.refreshToken=:token WHERE m.idx=:idx")
    void updateRefreshToken(@Param("idx") Long idx, @Param("token") String token);
}

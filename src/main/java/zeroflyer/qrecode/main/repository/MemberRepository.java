package zeroflyer.qrecode.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.main.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}

package zeroflyer.qrecode.sub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.sub.domain.PointLog;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    @Query(value = "SELECT SUM(p.point) FROM PointLog p WHERE p.memberId = ?1")
    Long sumByMemberId(String memberId);
}

package zeroflyer.qrecode.sub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.sub.domain.FlyerLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlyerLogRepository extends JpaRepository<FlyerLog, Long> {
    List<FlyerLog> findAllByMemberId(String memberId);

    Optional<FlyerLog> findByFlyerIdxAndMemberId(Long idx, String memberId);
}

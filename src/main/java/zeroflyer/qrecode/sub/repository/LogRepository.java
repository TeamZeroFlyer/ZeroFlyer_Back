package zeroflyer.qrecode.sub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.sub.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}

package zeroflyer.qrecode.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.main.domain.QR;

import java.util.List;
import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<QR, Long> {
    Optional<QR> findByQrNum(String num);

    List<QR> findAllByQrStoreIdxOrderByQrTimeStampDesc(Long idx);

    @Query(value = "SELECT COUNT(*) FROM QR q WHERE q.qrEnd > NOW() AND q.qrFlyerIdx=?1", nativeQuery = true)
    Long countByQrFlyerIdx(Long idx);

    @Query(value = "SELECT COUNT(*) FROM QR q WHERE q.qrEnd > NOW() AND q.qrStoreIdx=?1", nativeQuery = true)
    Long countByQrStoreIdx(Long idx);
}

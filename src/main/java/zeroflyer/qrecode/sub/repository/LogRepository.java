package zeroflyer.qrecode.sub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.sub.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Long countByMemberId(String memberId);

    Long countByStoreIdx(Long storeIdx);

    Long countByQrIdx(Long qrIdx);

    Long countByFlyerIdx(Long flyerIdx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.timestamp BETWEEN CONCAT(?1,' 00:00:00') AND CONCAT(?1,' 10:29:59')) AND (l.storeIdx = ?2)", nativeQuery = true)
    Long countToNine(String date, Long idx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.timestamp BETWEEN CONCAT(?1,' 10:30:00') AND CONCAT(?1,' 13:29:59')) AND (l.storeIdx = ?2)", nativeQuery = true)
    Long countToTwelve(String date, Long idx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.timestamp BETWEEN CONCAT(?1,' 13:30:00') AND CONCAT(?1,' 16:29:59')) AND (l.storeIdx = ?2)", nativeQuery = true)
    Long countToFifteen(String date, Long idx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.timestamp BETWEEN CONCAT(?1,' 16:30:00') AND CONCAT(?1,' 19:29:59')) AND (l.storeIdx = ?2)", nativeQuery = true)
    Long countToEighteen(String date, Long idx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.timestamp BETWEEN CONCAT(?1,' 19:30:00') AND CONCAT(?1,' 23:59:59')) AND (l.storeIdx = ?2)", nativeQuery = true)
    Long countToTwentyOne(String date, Long idx);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.storeIdx=?1) AND (l.timestamp BETWEEN CONCAT(?2,' 00:00:00') AND CONCAT(?2,' 23:59:59'))", nativeQuery = true)
    Long countByPercent(Long idx, String data);

    @Query(value = "SELECT count(*) FROM Log l WHERE (l.storeIdx=?1) AND (l.timestamp BETWEEN ?2 AND DATE_ADD(?2, INTERVAL 6 DAY))", nativeQuery = true)
    Long countByWeekly(Long idx, String target);
}

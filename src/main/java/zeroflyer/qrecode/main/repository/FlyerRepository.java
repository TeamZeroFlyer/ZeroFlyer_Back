package zeroflyer.qrecode.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.main.domain.Flyer;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlyerRepository extends JpaRepository<Flyer, Long> {
    Optional<Flyer> findByFlyerStoreIdx(Long idx);

    List<Flyer> findAllByFlyerStoreIdx(Long idx);

    @Query(value = "SELECT f.hasCoupon FROM Flyer f WHERE f.flyerStoreIdx = ?1")
    List<Boolean> findHasCouponByFlyerStoreIdx(Long idx);

    List<Flyer> findAllByFlyerStoreIdxAndIsValid(Long idx, Boolean valid);
}

package zeroflyer.qrecode.main.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zeroflyer.qrecode.main.domain.Store;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT * FROM Store s WHERE (CAST(s.storeLat AS DECIMAL(10,6)) BETWEEN ?1 AND ?2) AND (CAST(s.storeLng AS DECIMAL(10,6)) BETWEEN ?3 AND ?4)", nativeQuery = true)
    List<Store> getStoreByLatAndLng(Double latStart, Double latEnd, Double lngStart, Double lngEnd);

    @Query(value = "SELECT * FROM Store s WHERE (s.storeName LIKE %?1%) OR (s.storeTag LIKE %?1%)", nativeQuery = true)
    List<Store> getStoreByKeyword(String keyword);

    Optional<Store> findByMemberIdx(Long memberIdx);

    Optional<Store> findStoreByMemberIdx(Long memberIdx);
}

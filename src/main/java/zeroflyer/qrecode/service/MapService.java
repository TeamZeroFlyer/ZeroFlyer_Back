package zeroflyer.qrecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroflyer.qrecode.dto.map.ResponseStoreInfoDto;
import zeroflyer.qrecode.main.domain.Flyer;
import zeroflyer.qrecode.main.domain.Store;
import zeroflyer.qrecode.main.repository.FlyerRepository;
import zeroflyer.qrecode.main.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {
    private final StoreRepository storeRepository;
    private final FlyerRepository flyerRepository;

    @Autowired
    public MapService(StoreRepository storeRepository, FlyerRepository flyerRepository) {
        this.storeRepository = storeRepository;
        this.flyerRepository = flyerRepository;
    }

    public List<ResponseStoreInfoDto> getStoreInfo(String lat, String lng) {
        Double latStart = Math.round((Double.parseDouble(lat)-0.01)*1000000) / 1000000.0;
        Double latEnd = Math.round((Double.parseDouble(lat)+0.01)*1000000) / 1000000.0;
        Double lngStart = Math.round((Double.parseDouble(lng)-0.005)*1000000) / 1000000.0;
        Double lngEnd = Math.round((Double.parseDouble(lng)+0.005)*1000000) / 1000000.0;

        List<ResponseStoreInfoDto> result = new ArrayList<>();

        List<Store> temp = storeRepository.getStoreByLatAndLng(latStart, latEnd, lngStart, lngEnd);

        for (Store store : temp) {
            List<Boolean> hasCouponList = flyerRepository.findHasCouponByFlyerStoreIdx(store.getIdx());

            boolean hasCoupon = false;

            if (hasCouponList.contains(true)){
                hasCoupon = true;
            }

            ResponseStoreInfoDto dto = new ResponseStoreInfoDto(store, hasCoupon);
            result.add(dto);
        }

        return result;
    }

    public List<ResponseStoreInfoDto> getStoreInfoByKeyword(String keyword) {
        List<ResponseStoreInfoDto> result = new ArrayList<>();

        List<Store> temp = storeRepository.getStoreByKeyword(keyword);

        for (Store store : temp) {
            List<Boolean> hasCouponList = flyerRepository.findHasCouponByFlyerStoreIdx(store.getIdx());

            boolean hasCoupon = false;

            if (hasCouponList.contains(true)){
                hasCoupon = true;
            }

            ResponseStoreInfoDto dto = new ResponseStoreInfoDto(store, hasCoupon);
            result.add(dto);
        }
        return result;
    }
}

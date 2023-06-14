package zeroflyer.qrecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeroflyer.qrecode.dto.store.*;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.main.domain.Flyer;
import zeroflyer.qrecode.main.domain.Member;
import zeroflyer.qrecode.main.domain.Store;
import zeroflyer.qrecode.main.repository.FlyerRepository;
import zeroflyer.qrecode.main.repository.MemberRepository;
import zeroflyer.qrecode.main.repository.QrRepository;
import zeroflyer.qrecode.main.repository.StoreRepository;
import zeroflyer.qrecode.sub.repository.LogRepository;
import zeroflyer.qrecode.util.jwt.JwtTokenProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final FlyerRepository flyerRepository;
    private final LogRepository logRepository;
    private final QrRepository qrRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository, FlyerRepository flyerRepository, LogRepository logRepository, QrRepository qrRepository) {
        this.storeRepository = storeRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.flyerRepository = flyerRepository;
        this.logRepository = logRepository;
        this.qrRepository = qrRepository;
    }

    @Transactional
    public String insertStoreInfo(RequestStoreInfoDto requestStoreInfoDto) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        String lat = String.format("%.6f", requestStoreInfoDto.getLat());
        String lng = String.format("%.6f", requestStoreInfoDto.getLng());

        if (storeRepository.findStoreByMemberIdx(member.getIdx()).isPresent()) {
            Store store = storeRepository.findStoreByMemberIdx(member.getIdx()).get();
            store.setUpdate(requestStoreInfoDto, lat, lng);

            storeRepository.save(store);
        } else {
            Store store = Store.builder()
                    .dto(requestStoreInfoDto)
                    .lat(lat)
                    .lng(lng)
                    .memberIdx(member.getIdx())
                    .build();

            storeRepository.save(store);
        }

        return "success";
    }

    public ResponseStoreDto getStoreInfo() {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        Store store = storeRepository.findByMemberIdx(member.getIdx())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        return ResponseStoreDto.builder()
                .store(store)
                .build();
    }

    public void insertFlyerInfo(RequestFlyerInfoDto flyerInfoDto) {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Flyer flyer = Flyer.builder()
                .dto(flyerInfoDto)
                .idx(store.getIdx())
                .build();

        flyerRepository.save(flyer);
    }

    public void updateFlyerInfo(RequestFlyerInfoDto flyerInfoDto, Long flyerIdx) {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Flyer flyer = flyerRepository.findById(flyerIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        if (!Objects.equals(store.getIdx(), flyer.getFlyerStoreIdx())) {
            throw new PrivateException(StatusCode.WRONG_REQUEST);
        }

        flyer.setUpdate(flyerInfoDto);

        flyerRepository.save(flyer);
    }

    public List<ResponseFlyerDto> getFlyers() {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        List<Flyer> flyers = flyerRepository.findAllByFlyerStoreIdxAndIsValid(store.getIdx(), true);

        List<ResponseFlyerDto> result = new ArrayList<>();

        for (Flyer flyer : flyers) {

            Long qrCount = qrRepository.countByQrFlyerIdx(flyer.getIdx());
            Long scanCount = logRepository.countByFlyerIdx(flyer.getIdx());

            ResponseFlyerDto dto = ResponseFlyerDto.builder()
                    .qrCount(qrCount)
                    .scanCount(scanCount)
                    .flyer(flyer)
                    .build();

            result.add(dto);
        }

        return result;
    }

    public ResponseFlyerDto getFlyer(Long flyerid) {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Flyer flyer = flyerRepository.findById(flyerid)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        if (!flyer.getIsValid()) {
            throw new PrivateException(StatusCode.NOT_FOUND_DATA);
        }

        Long qrCount = qrRepository.countByQrFlyerIdx(flyer.getIdx());
        Long scanCount = logRepository.countByFlyerIdx(flyer.getIdx());

        return ResponseFlyerDto.builder()
                .flyer(flyer)
                .qrCount(qrCount)
                .scanCount(scanCount)
                .build();
    }

    public ResponseHomeDto getHomeInfo() throws Exception {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Long storeIdx = store.getIdx();

        Date date = new Date();

        String dataPattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(dataPattern);

        String today = format.format(date);

        Date selectDate = format.parse(today);

        Calendar cal = new GregorianCalendar(Locale.KOREA);

        cal.setTime(selectDate);
        cal.add(Calendar.DATE, -1);

        String yesterday = format.format(cal.getTime());

        Long storeScanCount = logRepository.countByStoreIdx(storeIdx);
        DailyChartDto todayChart = getDailyChart(today, storeIdx);
        DailyChartDto yesterdayChart = getDailyChart(yesterday, storeIdx);
        Float percent = getPercentChart(storeIdx, today);
        Map<String, Long> weekly = getWeeklyChart(storeIdx, today);

        Long qrCount = qrRepository.countByQrStoreIdx(storeIdx);

        String flyerUrl = null;
        List<Flyer> flyers = flyerRepository.findAllByFlyerStoreIdxAndIsValid(storeIdx, true);
        if (flyers.size() != 0){
            flyerUrl = flyers.get(0).getFlyerUrl();
        }

        return ResponseHomeDto.builder()
                .yesterday(yesterdayChart)
                .today(todayChart)
                .percent(percent)
                .first_week(weekly.get("first"))
                .second_week(weekly.get("second"))
                .third_week(weekly.get("third"))
                .storeName(store.getStoreName())
                .storeUrl(flyerUrl)
                .storeQrCount(qrCount)
                .storeScanCount(storeScanCount)
                .userName(member.getMemberName())
                .build();
    }

    public DailyChartDto getDailyChart(String date, Long idx) {
        Long dateNine = logRepository.countToNine(date, idx);
        Long dateTwelve = logRepository.countToTwelve(date, idx);
        Long dateFifteen = logRepository.countToFifteen(date, idx);
        Long dateEighteen = logRepository.countToEighteen(date, idx);
        Long dateTwentyOne = logRepository.countToTwentyOne(date, idx);

        return DailyChartDto.builder()
                .nine(dateNine)
                .twelve(dateTwelve)
                .fifteen(dateFifteen)
                .eighteen(dateEighteen)
                .twentyOne(dateTwentyOne)
                .build();
    }

    public Float getPercentChart(Long idx, String now) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date selectDate = format.parse(now);

        Calendar cal = new GregorianCalendar(Locale.KOREA);

        cal.setTime(selectDate);
        Long first = logRepository.countByPercent(idx, format.format(cal.getTime()));
        cal.add(Calendar.DATE, -7);
        Long second = logRepository.countByPercent(idx, format.format(cal.getTime()));

        if (second == 0) {
            return 999.99f;
        }
        Float result = (float) first / second * 100 - 100;
        String percent = String.format("%.2f", result);

        return Float.parseFloat(percent);
    }

    public Map<String, Long> getWeeklyChart(Long idx, String now) throws ParseException {
        Map<String, Long> result = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date selectDate = format.parse(now);

        Calendar cal = new GregorianCalendar(Locale.KOREA);

        cal.setTime(selectDate);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        int target = 2-dayOfWeek;

        cal.add(Calendar.DATE, target);
        Long firstWeek = logRepository.countByWeekly(idx, format.format(cal.getTime()));

        cal.add(Calendar.DATE, -7);
        Long secondWeek = logRepository.countByWeekly(idx, format.format(cal.getTime()));

        cal.add(Calendar.DATE, -7);
        Long thirdWeek = logRepository.countByWeekly(idx, format.format(cal.getTime()));

        result.put("first", firstWeek);
        result.put("second", secondWeek);
        result.put("third", thirdWeek);

        return result;
    }

    public String deleteFlyer(Long idx) {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Flyer flyer = flyerRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        if (!flyer.getIsValid()) {
            throw new PrivateException(StatusCode.NOT_FOUND_DATA);
        }
        if (!flyer.getFlyerStoreIdx().equals(store.getIdx())) {
            throw new PrivateException(StatusCode.WRONG_REQUEST);
        }

        flyer.delete();

        flyerRepository.save(flyer);

        return "success";
    }
}

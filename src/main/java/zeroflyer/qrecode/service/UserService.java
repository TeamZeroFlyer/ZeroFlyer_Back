package zeroflyer.qrecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroflyer.qrecode.dto.user.PointLogDto;
import zeroflyer.qrecode.dto.user.ResponseFlyerForUserDto;
import zeroflyer.qrecode.dto.user.ResponsePointInfoDto;
import zeroflyer.qrecode.dto.user.ResponseUserInfoDto;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.main.domain.Flyer;
import zeroflyer.qrecode.main.domain.Grade;
import zeroflyer.qrecode.main.domain.Member;
import zeroflyer.qrecode.main.domain.Store;
import zeroflyer.qrecode.main.repository.FlyerRepository;
import zeroflyer.qrecode.main.repository.MemberRepository;
import zeroflyer.qrecode.main.repository.StoreRepository;
import zeroflyer.qrecode.sub.domain.FlyerLog;
import zeroflyer.qrecode.sub.domain.PointLog;
import zeroflyer.qrecode.sub.repository.FlyerLogRepository;
import zeroflyer.qrecode.sub.repository.LogRepository;
import zeroflyer.qrecode.sub.repository.PointLogRepository;
import zeroflyer.qrecode.util.jwt.JwtTokenProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LogRepository logRepository;
    private final PointLogRepository pointLogRepository;
    private final FlyerRepository flyerRepository;
    private final FlyerLogRepository flyerLogRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public UserService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, LogRepository logRepository, PointLogRepository pointLogRepository, FlyerRepository flyerRepository, FlyerLogRepository flyerLogRepository, StoreRepository storeRepository) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.logRepository = logRepository;
        this.pointLogRepository = pointLogRepository;
        this.flyerRepository = flyerRepository;
        this.flyerLogRepository = flyerLogRepository;
        this.storeRepository = storeRepository;
    }

    public HashMap<String, Object> getUserStatus() {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        HashMap<String, Object> result = new HashMap<>();
        result.put("lastStatus", member.getGrade().toString());
        return result;
    }

    public String updateUserStatus(String status) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        try {
            member.setGrade(Grade.valueOf(status));
            memberRepository.save(member);
            return "success";
        } catch (Exception e) {
            return "false";
        }
    }

    public ResponseUserInfoDto getHomeInfo() {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        String userName = member.getMemberName();
        Long userScanCount = logRepository.countByMemberId(member.getMemberId());
        Long userPoint = pointLogRepository.sumByMemberId(member.getMemberId());
        if (userPoint == null) {
            userPoint = 0L;
        }

        return ResponseUserInfoDto.builder()
                .userName(userName)
                .userScanCount(userScanCount)
                .userPoint(userPoint)
                .build();
    }

    public String saveFlyer(Long idx) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        Flyer flyer = flyerRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        FlyerLog log = flyerLogRepository.findByFlyerIdxAndMemberId(flyer.getIdx(), member.getMemberId())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_DATA));

        if (log.getFlyerIdx().equals(idx)) {
            throw new PrivateException(StatusCode.EXIST_FLYER);
        }

        FlyerLog flyerLog = FlyerLog.builder()
                .flyerIdx(flyer.getIdx())
                .memberId(member.getMemberId())
                .build();

        flyerLogRepository.save(flyerLog);

        return "success";
    }

    public List<ResponseFlyerForUserDto> getFlyerLog() {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        List<FlyerLog> logList = flyerLogRepository.findAllByMemberId(member.getMemberId());

        List<ResponseFlyerForUserDto> result = new ArrayList<>();

        for (FlyerLog flyerLog : logList) {
            Long idx = flyerLog.getFlyerIdx();

            Flyer flyer = flyerRepository.findById(idx)
                    .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

            ResponseFlyerForUserDto dto = ResponseFlyerForUserDto.builder()
                    .flyer(flyer)
                    .build();

            result.add(dto);
        }

        return result;
    }

    public String deleteFlyer(Long idx) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        Flyer flyer = flyerRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        FlyerLog flyerLog = flyerLogRepository.findByFlyerIdxAndMemberId(flyer.getIdx(), member.getMemberId())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_DATA));

        flyerLogRepository.delete(flyerLog);

        return "success";
    }

    public ResponsePointInfoDto getPointLog() {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        String memberId = member.getMemberId();

        List<PointLog> logList = pointLogRepository.findAllByMemberId(memberId);

        List<PointLogDto> dtoList = new ArrayList<>();

        for (PointLog log : logList) {
            Store store = storeRepository.findById(log.getStoreIdx())
                    .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

            PointLogDto dto = PointLogDto.builder()
                    .storeName(store.getStoreName())
                    .point(log.getPoint())
                    .timestamp(log.getTimestamp())
                    .build();

            dtoList.add(dto);
        }

        Long scanCount = logRepository.countByMemberId(memberId);
        Long totalPoint = pointLogRepository.sumByMemberId(memberId);

        return ResponsePointInfoDto.builder()
                .totalPoint(totalPoint)
                .logList(dtoList)
                .scanCount(scanCount)
                .build();
    }

    public String getFlyerUrl(Long idx) {
        Flyer flyer = flyerRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        return flyer.getFlyerUrl();
    }
}

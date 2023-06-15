package zeroflyer.qrecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroflyer.qrecode.dto.qr.*;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.main.domain.Flyer;
import zeroflyer.qrecode.main.domain.Member;
import zeroflyer.qrecode.main.domain.QR;
import zeroflyer.qrecode.main.domain.Store;
import zeroflyer.qrecode.main.repository.FlyerRepository;
import zeroflyer.qrecode.main.repository.MemberRepository;
import zeroflyer.qrecode.main.repository.QrRepository;
import zeroflyer.qrecode.main.repository.StoreRepository;
import zeroflyer.qrecode.sub.domain.Log;
import zeroflyer.qrecode.sub.domain.PointLog;
import zeroflyer.qrecode.sub.repository.LogRepository;
import zeroflyer.qrecode.sub.repository.PointLogRepository;
import zeroflyer.qrecode.util.jwt.JwtTokenProvider;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QrService {

    private final LogRepository logRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PointLogRepository pointLogRepository;
    private final FlyerRepository flyerRepository;
    private final QrRepository qrRepository;
    private final StoreRepository storeRepository;


    @Autowired
    public QrService(LogRepository logRepository, JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository, PointLogRepository pointLogRepository, FlyerRepository flyerRepository, QrRepository qrRepository, StoreRepository storeRepository) {
        this.logRepository = logRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.pointLogRepository = pointLogRepository;
        this.flyerRepository = flyerRepository;
        this.qrRepository = qrRepository;
        this.storeRepository = storeRepository;
    }

    public void insertLog(RequestLoggingDto requestLoggingDto) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Member member = memberRepository.findByIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_MEMBER));

        Log log = Log.builder()
                .memberId(member.getMemberId())
                .storeIdx(requestLoggingDto.getStoreIdx())
                .flyerIdx(requestLoggingDto.getFlyerIdx())
                .qrIdx(requestLoggingDto.getQrIdx())
                .build();

        PointLog pointLog = PointLog.builder()
                .point(10L)
                .memberId(member.getMemberId())
                .storeIdx(requestLoggingDto.getStoreIdx())
                .build();

        pointLogRepository.save(pointLog);
        logRepository.save(log);
    }

    public String insertQrInfo(RequestQrMadeDto requestQrMadeDto) {
        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        Long flyerIdx = requestQrMadeDto.getQrFlyerIdx();
        List<QRPtjDto> ptjList = requestQrMadeDto.getQrPtj();
        Flyer flyer = flyerRepository.findById(flyerIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        Timestamp start = flyer.getFlyerStart();
        Timestamp end = flyer.getFlyerEnd();

        for (QRPtjDto dto : ptjList) {
            String qrNum = randomNum();

            QR qr = QR.builder()
                    .qrStoreIdx(store.getIdx())
                    .dto(dto)
                    .qrFlyerIdx(flyerIdx)
                    .qrNum(qrNum)
                    .qrStart(start)
                    .qrEnd(end)
                    .build();

            qrRepository.save(qr);
        }

        return "success";
    }

    public String randomNum() {
        Random random = new Random();
        int createNum;
        String ranNum;
        int letter = 8;
        StringBuilder resultNum = new StringBuilder();

        for (int i = 0; i < letter; i++) {
            createNum = random.nextInt(9);
            ranNum = Integer.toString(createNum);
            resultNum.append(ranNum);
        }

        String result = "QR"+resultNum;

        if (qrRepository.findByQrNum(result).isPresent()) {
            randomNum();
        }

        return result;
    }

    public ResponseQrInfoDto getQrInfo(Long idx) {

        QR qr = qrRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_QR));

        Flyer flyer = flyerRepository.findById(qr.getQrFlyerIdx())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        Long count = logRepository.countByQrIdx(idx);

        return ResponseQrInfoDto.builder()
                .qr(qr)
                .flyerName(flyer.getFlyerName())
                .scanCount(count)
                .build();
    }

    public List<ResponseQrListDto> getQrsInfo() {

        Long memberIdx = jwtTokenProvider.getUserInfo();

        Store store = storeRepository.findStoreByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        List<QR> qrList = qrRepository.findAllByQrStoreIdxOrderByQrTimeStampDesc(store.getIdx());

        List<ResponseQrListDto> result = new ArrayList<>();
        List<ResponseQrInfoDto> qrs = new ArrayList<>();
        if (qrList.size() == 0) {
            return result;
        }
        Date target = qrList.get(0).getQrTimeStamp();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTarget = format.format(target);

        for (QR qr : qrList) {
            Flyer flyer = flyerRepository.findById(qr.getQrFlyerIdx())
                    .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));
            Long count = logRepository.countByQrIdx(qr.getIdx());

            if (!format.format(qr.getQrTimeStamp()).equals(strTarget)) {
                ResponseQrListDto temp2 = ResponseQrListDto.builder()
                        .date(strTarget)
                        .qrList(qrs)
                        .build();

                result.add(temp2);

                qrs = new ArrayList<>();

                strTarget = format.format(qr.getQrTimeStamp());

                ResponseQrInfoDto dto = ResponseQrInfoDto.builder()
                        .qr(qr)
                        .flyerName(flyer.getFlyerName())
                        .scanCount(count)
                        .build();

                qrs.add(dto);

            } else {
                ResponseQrInfoDto dto = ResponseQrInfoDto.builder()
                        .qr(qr)
                        .flyerName(flyer.getFlyerName())
                        .scanCount(count)
                        .build();

                qrs.add(dto);
            }
        }

        ResponseQrListDto temp2 = ResponseQrListDto.builder()
                .date(strTarget)
                .qrList(qrs)
                .build();

        result.add(temp2);

        return result;
    }

    public String deleteQr(Long idx) {
        QR qr = qrRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_QR));
        qrRepository.delete(qr);
        return "success";
    }

    public String updateQr(Long idx, UpdateQrDto updateQrDto) {
        QR qr = qrRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_QR));

        Flyer flyer = flyerRepository.findById(qr.getQrFlyerIdx())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        qr.updateQr(updateQrDto, flyer.getFlyerStart(), flyer.getFlyerEnd());

        qrRepository.save(qr);
        return "success";
    }

    public ResponseScanDto scanQr(Long idx) {
        Long memberIdx = jwtTokenProvider.getUserInfo();
        Store store = storeRepository.findByMemberIdx(memberIdx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_STORE));

        QR qr = qrRepository.findById(idx)
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_QR));

        Flyer flyer = flyerRepository.findById(qr.getQrFlyerIdx())
                .orElseThrow(() -> new PrivateException(StatusCode.NOT_FOUND_FLYER));

        Long scanCount = logRepository.countByQrIdx(idx);

        return ResponseScanDto.builder()
                .storeIdx(store.getIdx())
                .storeName(store.getStoreName())
                .qrScanCount(scanCount)
                .qrNum(qr.getQrNum())
                .qrTimestamp(qr.getQrTimeStamp())
                .flyerIdx(flyer.getIdx())
                .flyerUrl(flyer.getFlyerUrl())
                .build();
    }

}

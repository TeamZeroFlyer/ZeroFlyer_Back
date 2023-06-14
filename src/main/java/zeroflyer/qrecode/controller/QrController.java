package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import zeroflyer.qrecode.dto.qr.RequestLoggingDto;
import zeroflyer.qrecode.dto.qr.RequestQrMadeDto;
import zeroflyer.qrecode.dto.qr.UpdateQrDto;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.service.QrService;

@RestController
@RequestMapping("/qr")
public class QrController {
    private final QrService qrService;

    @Autowired
    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    @PostMapping("/logging")
    public ResponseEntity<PrivateResponseBody> getStoreInfo(@RequestBody RequestLoggingDto requestLoggingDto) {
        qrService.insertLog(requestLoggingDto);
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, "test"), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<PrivateResponseBody> insertQrInfo(@RequestBody RequestQrMadeDto requestQrMadeDto) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.insertQrInfo(requestQrMadeDto)), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PrivateResponseBody> getQrInfo(@RequestParam(defaultValue = "0") Long idx) {
        if (idx == 0) {
            return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.getQrsInfo()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.getQrInfo(idx)), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PrivateResponseBody> deleteQr(@RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.deleteQr(idx)), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<PrivateResponseBody> updateQr(@RequestBody UpdateQrDto updateQrDto, @RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.updateQr(idx, updateQrDto)), HttpStatus.OK);
    }

    @GetMapping("/scan")
    public ResponseEntity<PrivateResponseBody> scanQr(@RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, qrService.scanQr(idx)), HttpStatus.OK);
    }
}

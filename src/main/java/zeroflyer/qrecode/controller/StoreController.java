package zeroflyer.qrecode.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zeroflyer.qrecode.dto.store.RequestFlyerInfoDto;
import zeroflyer.qrecode.dto.store.RequestStoreInfoDto;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.service.S3Uploader;
import zeroflyer.qrecode.service.StoreService;

import java.io.IOException;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final S3Uploader s3Uploader;

    @Autowired
    public StoreController(StoreService storeService, S3Uploader s3Uploader) {
        this.storeService = storeService;
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/setstore")
    public ResponseEntity<PrivateResponseBody> insertStoreInfo(@RequestBody RequestStoreInfoDto requestStoreInfoDto) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.insertStoreInfo(requestStoreInfoDto)), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<PrivateResponseBody> getStoreInfo() {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.getStoreInfo()), HttpStatus.OK);
    }

    @PostMapping("/uploadflyer")
    public ResponseEntity<PrivateResponseBody> uploadFlyer(@RequestPart("image")MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, s3Uploader.upload(multipartFile, "flyer")), HttpStatus.OK);
    }

    @PostMapping("/uploadflyer/info")
    public ResponseEntity<PrivateResponseBody> uploadFlyerInfo(@RequestBody RequestFlyerInfoDto flyerInfoDto) {
        storeService.insertFlyerInfo(flyerInfoDto);
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, "success"), HttpStatus.OK);
    }

    @PostMapping("/updateflyer/info")
    public ResponseEntity<PrivateResponseBody> updateFlyerInfo(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestBody RequestFlyerInfoDto flyerInfoDto, @RequestParam Long flyerid) {
        storeService.updateFlyerInfo(flyerInfoDto, flyerid);
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, "success"), HttpStatus.OK);
    }

    @GetMapping("/flyer")
    public ResponseEntity<PrivateResponseBody> getFlyer(@RequestParam(defaultValue = "0") Long flyerid) {
        if (flyerid == 0) {
            return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.getFlyers()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.getFlyer(flyerid)), HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<PrivateResponseBody> getHomeInfo() throws Exception {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.getHomeInfo()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PrivateResponseBody> getHomeInfo(@RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, storeService.deleteFlyer(idx)), HttpStatus.OK);
    }

}

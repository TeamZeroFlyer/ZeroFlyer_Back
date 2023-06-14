package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroflyer.qrecode.exception.PrivateException;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.service.MapService;

@RestController
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/stores")
    public ResponseEntity<PrivateResponseBody> getStoreInfo(@RequestParam String lat, @RequestParam String lng) {

        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, mapService.getStoreInfo(lat, lng)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PrivateResponseBody> getStoreInfoByKeyword(@RequestParam String keyword) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, mapService.getStoreInfoByKeyword(keyword)), HttpStatus.OK);
    }
}

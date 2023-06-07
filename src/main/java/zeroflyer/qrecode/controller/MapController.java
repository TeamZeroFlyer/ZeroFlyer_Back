package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<PrivateResponseBody> getStoreInfo() {

        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, "test"), HttpStatus.OK);
    }
}

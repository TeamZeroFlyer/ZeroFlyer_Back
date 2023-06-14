package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public ResponseEntity<PrivateResponseBody> getUserStatus() {
        HashMap<String, Object> status = userService.getUserStatus();
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, status), HttpStatus.OK);
    }

    @PostMapping("/setstatus")
    public ResponseEntity<PrivateResponseBody> updateUserStatus(@RequestBody HashMap<String, Object> status) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, userService.updateUserStatus(status.get("lastStatus").toString())), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PrivateResponseBody> getHomeInfo() {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, userService.getHomeInfo()), HttpStatus.OK);
    }

    @GetMapping("/saveflyer")
    public ResponseEntity<PrivateResponseBody> saveFlyer(@RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, userService.saveFlyer(idx)), HttpStatus.OK);
    }

    @GetMapping("/flyer")
    public ResponseEntity<PrivateResponseBody> getFlyerLog() {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, userService.getFlyerLog()), HttpStatus.OK);
    }

    @DeleteMapping("/flyer")
    public ResponseEntity<PrivateResponseBody> deleteFlyer(@RequestParam Long idx) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, userService.deleteFlyer(idx)), HttpStatus.OK);
    }
}

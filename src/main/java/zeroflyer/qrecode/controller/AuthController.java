package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
//    private final AuthService authService;
//
//    @Autowired
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

    @PostMapping("/check")
    public ResponseEntity<PrivateResponseBody> checkMemberInfo(@RequestBody HashMap<String, Object> accessToken) {
        String token = (String) accessToken.get("accessToken");
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, "test"), HttpStatus.OK);
    }
}

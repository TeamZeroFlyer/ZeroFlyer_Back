package zeroflyer.qrecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroflyer.qrecode.exception.PrivateResponseBody;
import zeroflyer.qrecode.exception.StatusCode;
import zeroflyer.qrecode.util.jwt.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/refresh")
    public ResponseEntity<PrivateResponseBody> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(new PrivateResponseBody(StatusCode.OK, authService.refreshToken(request, response)), HttpStatus.OK);
    }
}

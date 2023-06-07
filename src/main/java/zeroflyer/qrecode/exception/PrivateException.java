package zeroflyer.qrecode.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class PrivateException extends RuntimeException {
    private final StatusCode statusCode;

    public PrivateException(StatusCode statusCode) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
    }
}

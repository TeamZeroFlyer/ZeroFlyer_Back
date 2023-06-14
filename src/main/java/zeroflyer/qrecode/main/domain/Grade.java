package zeroflyer.qrecode.main.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    NONE("ROLE_NONE", "none"),
    USER("ROLE_USER", "user"),
    ADVERTISER("ROLE_ADVERTISER", "advertiser");

    private final String role;
    private final String name;
}

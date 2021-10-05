package kr.sproutfx.platform.authservice.common.enumeration;

import lombok.Getter;

@Getter
public enum ErrorStatus {
    // Unhandled
    UNHANDLED("-1", "Unhandled Error"),
    // Common(10)
    UNAUTHORIZED("100001", "Incorrect email or password.");

    private final String value;
    private final String reason;
    
    private ErrorStatus(String value, String reason) {
        this.value = value;
        this.reason = reason;
    }
}
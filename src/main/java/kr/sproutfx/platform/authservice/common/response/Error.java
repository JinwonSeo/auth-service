package kr.sproutfx.platform.authservice.common.response;

import kr.sproutfx.platform.authservice.common.enumeration.ErrorStatus;
import lombok.Getter;

@Getter
public class Error {
    private String value;
    private String reason;

    public Error(ErrorStatus errorStatus) {
        this.value = errorStatus.getValue();
        this.reason = errorStatus.getReason();
    }
}
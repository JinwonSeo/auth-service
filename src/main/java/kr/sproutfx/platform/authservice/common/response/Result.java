package kr.sproutfx.platform.authservice.common.response;

import kr.sproutfx.platform.authservice.common.enumeration.ErrorStatus;
import lombok.Getter;

@Getter
public class Result<T> {
    private boolean succeeded;
    private Error error;
    private T data;

    public Result(T result) {
        this.succeeded = true;
        this.data = result;
        this.error = null;
    }

    public Result(ErrorStatus errorStatus) {
        this.succeeded = false;
        this.data = null;
        this.error = new Error(errorStatus);
    }
}

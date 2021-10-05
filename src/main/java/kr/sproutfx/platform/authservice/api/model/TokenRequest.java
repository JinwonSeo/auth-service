package kr.sproutfx.platform.authservice.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenRequest {
    private String email;
    private String password;
}

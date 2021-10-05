package kr.sproutfx.platform.authservice.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenRequestForRefresh {
    private String email;
    private String refreshToken;
}

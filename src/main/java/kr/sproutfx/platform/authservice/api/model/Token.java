package kr.sproutfx.platform.authservice.api.model;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Token {
    private String userId;
    private String userEmail;
    private String accessToken;
    private String refreshToken;
}
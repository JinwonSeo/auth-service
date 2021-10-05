package kr.sproutfx.platform.authservice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.sproutfx.platform.authservice.api.model.Token;
import kr.sproutfx.platform.authservice.api.model.TokenRequest;
import kr.sproutfx.platform.authservice.api.model.TokenRequestForRefresh;
import kr.sproutfx.platform.authservice.common.exception.UnauthorizedException;
import kr.sproutfx.platform.authservice.common.security.jwt.JwtProvider;
import kr.sproutfx.platform.authservice.common.security.service.UserService;

@Service
public class TokenService {
    UserService userService;
    JwtProvider jwtProvider;

    @Autowired
    public TokenService(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public Token generateToken(TokenRequest request) {
        
        String userId = userService.matches(request.getEmail(), request.getPassword());

        if (userId == null) throw new UnauthorizedException();

        return Token.builder()
            .userEmail(request.getEmail())
            .userId(userId)
            .accessToken(jwtProvider.createAccessToken(userId))
            .refreshToken(jwtProvider.createRefreshToken())
            .build();
    }
    
    public Token refreshToken(TokenRequestForRefresh request) {
        
        String userId = userService.exists(request.getEmail());
        String refreshToken = request.getRefreshToken();
        
        if (userId == null || refreshToken == null || !jwtProvider.validateRefreshToken(refreshToken)) throw new UnauthorizedException();

        return Token.builder()
            .userEmail(request.getEmail())
            .userId(userId)
            .accessToken(jwtProvider.createAccessToken(userId))
            .refreshToken(jwtProvider.createRefreshToken())
            .build();
    }
}

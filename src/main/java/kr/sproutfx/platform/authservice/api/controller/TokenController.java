package kr.sproutfx.platform.authservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

import org.springframework.web.bind.annotation.RequestBody;

import kr.sproutfx.platform.authservice.api.model.TokenRequest;
import kr.sproutfx.platform.authservice.api.model.TokenRequestForRefresh;
import kr.sproutfx.platform.authservice.api.service.TokenService;
import kr.sproutfx.platform.authservice.common.response.Result;

@RestController
@RequestMapping("/api/auth")
public class TokenController {
    TokenService service;
    
    @Autowired
    public TokenController(TokenService service) {
        this.service = service;
    }

    @PostMapping("/generate-token")
    @Timed(value = "api.auth.generate_token")
    public Result<Object> generateToken(@RequestBody TokenRequest request) {
        return new Result<>(service.generateToken(request));
    }

    @PostMapping("/refresh-token")
    @Timed(value = "api.auth.refresh_token")
    public Result<Object> refreshToken(@RequestBody TokenRequestForRefresh request) {
        return new Result<>(service.refreshToken(request));
    }

    @GetMapping(value = "/test-token")
    public Result<Object> testToken(@AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(userDetails);
    }
}

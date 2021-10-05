package kr.sproutfx.platform.authservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sproutfx.platform.authservice.common.response.Result;

@RestController
@RequestMapping("/api/auth/common")
public class CommonController {
    Environment environment;
    
    @Autowired
    public CommonController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/hello")
    public Result<Object> hello(@AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(
            String.format("Hello! %s. This is %s:%s", 
                userDetails != null ? userDetails.getUsername() : "anonymous", 
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port")));
    }
}
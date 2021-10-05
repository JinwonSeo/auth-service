package kr.sproutfx.platform.authservice.common.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sproutfx.platform.authservice.common.security.mapper.UserMapper;
import kr.sproutfx.platform.authservice.common.security.model.User;

@RestController
@RequestMapping("/api/auth/common/security/user")
public class UserController {
    UserMapper mapper;

    @Autowired
    public UserController(UserMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public User selectById(@PathVariable String id) {
        return mapper.selectById(id);
    }
}

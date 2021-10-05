package kr.sproutfx.platform.authservice.common.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.sproutfx.platform.authservice.common.security.mapper.UserMapper;
import kr.sproutfx.platform.authservice.common.security.model.User;

@Service
public class UserService implements UserDetailsService {
    UserMapper mapper;
    PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
        User user = mapper.selectById(subject);

        if (user == null) return null;

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), new ArrayList<>());
    }

    public String matches(String email, String password) {
        User user = mapper.selectByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) return null;

        return user.getId();
    }

    public String exists(String email) {
        User user = mapper.selectByEmail(email);

        if (user == null) return null;

        return user.getId();
    }
}

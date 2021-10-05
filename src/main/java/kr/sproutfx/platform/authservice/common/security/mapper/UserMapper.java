package kr.sproutfx.platform.authservice.common.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.sproutfx.platform.authservice.common.security.model.User;

@Mapper
public interface UserMapper {
    User selectById(String id);
    User selectByEmail(String email);
}
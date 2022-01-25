package com.mybatis.dao;

import com.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> selectUserByName(@Param("username") String username);

    List<User> selectUserByLikeName(@Param("username") String username);

    List<User> selectUserByLikeNameRepair(@Param("username") String username);
}

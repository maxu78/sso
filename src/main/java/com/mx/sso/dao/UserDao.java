package com.mx.sso.dao;

import com.mx.sso.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    public List<User> getAllUser(User user);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(@Param("id") Integer id);
}

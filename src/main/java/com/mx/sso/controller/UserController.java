package com.mx.sso.controller;

import com.mx.sso.annotation.IsLogin;
import com.mx.sso.dao.UserRedisDao;
import com.mx.sso.pojo.User;
import com.mx.sso.service.UserService;
import com.mx.sso.util.JWTUtil;
import com.mx.sso.util.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Value("${cookie.key: sid}")
    private String cookieKey;

    @Autowired
    private UserRedisDao userRedisDao;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @IsLogin
    @PostMapping(value = "/register")
    public String register(User user, HttpServletRequest request) {
        user.setPassword(SHA256Util.encrypt(user.getPassword()));
        user.setIpAddress(request.getRemoteAddr());
        userService.register(user);
        return "ok";
    }

    @IsLogin
    @PostMapping(value = "/login")
    public User login(User user, HttpServletRequest request, HttpServletResponse response) {
        user.setPassword(SHA256Util.encrypt(user.getPassword()));
        List<User> users = userService.queryUser(user);
        if (users != null && users.size() > 0) {
            //生成token
//            String token = jwtUtil.generateToken(user.getUsername());
            String sessionId = UUID.randomUUID().toString();
            userRedisDao.set(sessionId, users.get(0).toString());
            //写到cookie中
            Cookie cookie = new Cookie(cookieKey, sessionId);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) userRedisDao.convertTime().toSeconds(userRedisDao.getExpiredTime()));
            cookie.setPath("/");
            response.addCookie(cookie);
            return users.get(0);
        }
        return null;
    }

    @GetMapping("/test")
    public String get(User user) {
//        userRedisDao.set(user.getUsername(), user);
        return "ok";
    }
}

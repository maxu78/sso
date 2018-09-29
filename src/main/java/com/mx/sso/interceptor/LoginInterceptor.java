package com.mx.sso.interceptor;

import com.google.gson.Gson;
import com.mx.sso.annotation.IsLogin;
import com.mx.sso.dao.UserRedisDao;
import com.mx.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${jwt.head: header}")
    private String headerToken;

    @Value("${jwt.uid: uid}")
    private String headerUid;

    @Value("${cookie.key: sid}")
    private String cookieKey;

    @Autowired
    private UserRedisDao userRedisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod method = null;
        try {
            method = (HandlerMethod) handler;
        } catch (Exception e) {
            return  false;
        }

        IsLogin isLogin = method.getMethodAnnotation(IsLogin.class);
        if (isLogin != null) {
            return true;
        }

        response.setCharacterEncoding("UTF-8");
//        String token = request.getHeader(headerToken);
//        String uid = request.getHeader(headerUid);

//        if (token == null || "".equals(token)) {
//            return false;
//        }

//        if (uid == null || "".equals(uid)) {
//            return false;
//        }

        //读取cookie
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieKey.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId == null || "".equals(sessionId)) {
            return false;
        }

        //读取redis
        String userJson = (String) userRedisDao.get(sessionId);
        if (userJson == null || "".equals(userJson)) {
            return false;
        }

        User user = null;
        try {
            user = new Gson().fromJson(userJson, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            return false;
        }

        return true;
    }
}

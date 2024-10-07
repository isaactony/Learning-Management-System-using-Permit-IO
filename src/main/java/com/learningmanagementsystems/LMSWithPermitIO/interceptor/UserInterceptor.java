package com.learningmanagementsystems.LMSWithPermitIO.interceptor;

import com.learningmanagementsystems.LMSWithPermitIO.service.UserService;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        String userKey = header != null ? header.replaceFirst("Bearer ", "") : null;

        if (userKey != null && !userKey.isEmpty()) {
            User user = userService.login(userKey);
            request.setAttribute("user", user);
        }
        return true;
    }
}


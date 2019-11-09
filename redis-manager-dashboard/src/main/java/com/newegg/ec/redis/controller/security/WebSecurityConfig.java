package com.newegg.ec.redis.controller.security;

import com.newegg.ec.redis.controller.oauth.AuthService;
import com.newegg.ec.redis.entity.User;
import com.newegg.ec.redis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by lzz on 2018/5/2.
 */

@Component
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuthService authService;

    @Autowired
    private IUserService userService;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        /*addInterceptor.excludePathPatterns("/login")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/signOut")
                .excludePathPatterns("/user/getUserFromSession")
                .excludePathPatterns("/system/getAuthorization")
                .excludePathPatterns("/system/getInstallationEnvironment");*/
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    public class SecurityInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String requestURI = request.getRequestURI();
            if (!Objects.equals(requestURI, "/user/oauth2Login")) {
                return true;
            }
            String code = request.getParameter("code");
            User user = authService.oauthLogin(code);
            if (user == null) {
                return false;
            }
            User realUser = userService.getUserByName(user.getUserName());
            if (realUser.getUserId() == null) {
                return false;
            }
            realUser.setAvatar(user.getAvatar());
            User userRole = userService.getUserRole(realUser.getGroupId(), realUser.getUserId());
            realUser.setUserRole(userRole.getUserRole());
            request.getSession().setAttribute("user", realUser);
            return true;
        }
    }
}
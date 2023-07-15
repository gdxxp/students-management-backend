package com.students.studentsmanagement.filter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.Header;
import cn.hutool.jwt.JWTUtil;
import com.students.studentsmanagement.constant.JwtConstant;
import com.students.studentsmanagement.constant.ResponseCodes;
import com.students.studentsmanagement.exception.BusinessException;
import com.students.studentsmanagement.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;


public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader(Header.AUTHORIZATION.getValue());
        if (ObjUtil.isEmpty(token)) {
            throw new BusinessException(ResponseCodes.TOKEN_EXPIRE, "token为空");
        }
        if (!JWTUtil.verify(request.getHeader(Header.AUTHORIZATION.getValue()), JwtConstant.JWT_KEY.getBytes())) {
            throw new BusinessException(ResponseCodes.TOKEN_EXPIRE, "token无效");
        }
        DateTime expireTime = JwtUtil.getExpireTime(request.getHeader(Header.AUTHORIZATION.getValue()));
        if (ObjUtil.isEmpty(expireTime) || DateTime.now().isAfter(expireTime)){
            throw new BusinessException(ResponseCodes.TOKEN_EXPIRE, "登录过期！请重新登陆！");
        }
        return true;
    }
}

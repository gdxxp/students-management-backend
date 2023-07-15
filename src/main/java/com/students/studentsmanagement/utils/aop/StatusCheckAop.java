package com.students.studentsmanagement.utils.aop;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.Header;
import com.students.studentsmanagement.constant.ResponseCodes;
import com.students.studentsmanagement.exception.BusinessException;
import com.students.studentsmanagement.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class StatusCheckAop {
    @Before("@annotation(com.students.studentsmanagement.utils.annotation.StatusCheck4User)")
    public void statusCheck(JoinPoint joinPoint){
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Integer status = JwtUtil.getStatus(request.getHeader(Header.AUTHORIZATION.getValue()));
        if(ObjUtil.isEmpty(status) || status.equals(1)){
            throw new BusinessException(ResponseCodes.NO_AUTH, "没有此项操作的权限！");
        }
    }
}

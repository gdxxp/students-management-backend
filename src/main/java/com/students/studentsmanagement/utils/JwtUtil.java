package com.students.studentsmanagement.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.google.common.collect.Maps;
import com.students.studentsmanagement.constant.JwtConstant;

import java.util.Map;


public class JwtUtil {

    public static Map<String, String> getUserJwtToken(String username, Integer status) {
        Map<String, Object> payload = Maps.newHashMapWithExpectedSize(6);
        DateTime dateTime = DateUtil.date();
        DateTime offsetDatetime = DateUtil.offsetDay(dateTime, 2);
        payload.put(JWT.ISSUED_AT, dateTime);
        payload.put(JWT.EXPIRES_AT, offsetDatetime);
        payload.put(JWT.NOT_BEFORE, dateTime);
        payload.put("name", username);
        payload.put("status", status);

        Map<String, String> tokenMap = Maps.newHashMapWithExpectedSize(1);
        tokenMap.put("token", JWTUtil.createToken(payload, JwtConstant.JWT_KEY.getBytes()));
        return tokenMap;
    }

    public static DateTime getExpireTime(String token) {
        String timestamp = JWTUtil.parseToken(token).getPayload(JWT.EXPIRES_AT).toString();
        return DateUtil.date(Long.parseLong(timestamp) * 1000);
    }

    public static Integer getStatus(String token) {
        return Integer.valueOf(JWTUtil.parseToken(token).getPayload("status").toString());
    }
}

package com.students.studentsmanagement.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.students.studentsmanagement.constant.ResponseCodes;
import com.students.studentsmanagement.domain.po.User;
import com.students.studentsmanagement.exception.BusinessException;
import com.students.studentsmanagement.service.UserService;
import com.students.studentsmanagement.mapper.UserMapper;
import com.students.studentsmanagement.utils.BaseResponse;
import com.students.studentsmanagement.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public BaseResponse<Object> login(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (ObjUtil.isEmpty(user)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "登陆失败！检查用户名和密码后重新登陆！");
        }
        Map<String, String> jwtToken = JwtUtil.getUserJwtToken(username, user.getStatus());
        return BaseResponse.success(jwtToken);
    }
}





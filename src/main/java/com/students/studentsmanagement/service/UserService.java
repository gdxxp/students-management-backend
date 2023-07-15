package com.students.studentsmanagement.service;

import com.students.studentsmanagement.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.students.studentsmanagement.utils.BaseResponse;


public interface UserService extends IService<User> {

    BaseResponse<Object> login(String username, String password);
}

package com.students.studentsmanagement.mapper;

import com.students.studentsmanagement.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface UserMapper extends BaseMapper<User> {

    User selectByUsernameAndPassword(String username, String password);
}





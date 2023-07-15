package com.students.studentsmanagement.controller;

import com.students.studentsmanagement.service.UserService;
import com.students.studentsmanagement.utils.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/management/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public BaseResponse<Object> login(@NotBlank String username,
                                      @NotBlank String password){
        return userService.login(username, password);
    }

    @GetMapping("/isExpire")
    public BaseResponse<Object> isExpire(){
        return BaseResponse.success();
    }
}

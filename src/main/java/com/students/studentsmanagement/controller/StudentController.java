package com.students.studentsmanagement.controller;

import com.students.studentsmanagement.service.StudentService;
import com.students.studentsmanagement.utils.BaseResponse;
import com.students.studentsmanagement.utils.annotation.StatusCheck4User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@RestController
@RequestMapping("/management/student")
public class StudentController {

    @Resource
    StudentService studentService;

    @GetMapping("/list")
    public BaseResponse<Object> listStudents(String searchInfo, Integer order, @NotNull Integer pageNum, @NotNull Integer pageNo){
        return studentService.listStudents(searchInfo, order, pageNum, pageNo);
    }

    @PutMapping("/update")
    @StatusCheck4User
    public BaseResponse<Object> updateStudent(@NotNull Long id,
                                              @NotNull Long stuId,
                                              @NotBlank String name,
                                              @NotNull Integer age,
                                              @NotNull Integer gender,
                                              @NotBlank String birthday,
                                              @NotBlank String address,
                                              @NotBlank String phone,
                                              @NotBlank String email){
        return studentService.updateStudent(id, stuId, name, age, gender, birthday, address, phone, email);
    }

    @DeleteMapping("/delete")
    @StatusCheck4User
    public BaseResponse<Object> deleteStudent(@NotNull  Long id){
        return studentService.deleteStudent(id);
    }

    @PostMapping("/add")
    @StatusCheck4User
    public BaseResponse<Object> addStudent(@NotNull Long stuId,
                                           @NotBlank String name,
                                           @NotNull Integer age,
                                           @NotNull Integer gender,
                                           @NotBlank String birthday,
                                           @NotBlank String address,
                                           @NotBlank String phone,
                                           @NotBlank String email) {
        return studentService.addStudent(stuId, name, age, gender, birthday, address, phone, email);
    }

    @PostMapping("/import")
    @StatusCheck4User
    public BaseResponse<Object> importStudents(@NotNull MultipartFile excel) throws IOException{
        return studentService.importStudents(excel);
    }
}

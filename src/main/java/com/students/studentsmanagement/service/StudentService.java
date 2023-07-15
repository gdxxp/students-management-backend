package com.students.studentsmanagement.service;

import com.students.studentsmanagement.domain.po.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.students.studentsmanagement.utils.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface StudentService extends IService<Student> {

    BaseResponse<Object> listStudents(String searchInfo, Integer order, Integer pageNum, Integer pageNo);

    BaseResponse<Object> deleteStudent(Long id);

    BaseResponse<Object> importStudents(MultipartFile excel) throws IOException;

    BaseResponse<Object> addStudent(Long stuId, String name, Integer age, Integer gender, String birthday, String address, String phone, String email);

    BaseResponse<Object> updateStudent(Long id, Long stuId, String name, Integer age, Integer gender, String birthday, String address, String phone, String email);
}

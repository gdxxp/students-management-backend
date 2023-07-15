package com.students.studentsmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.students.studentsmanagement.mapper")
public class StudentsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsManagementApplication.class, args);
    }

}

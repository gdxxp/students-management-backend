package com.students.studentsmanagement.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.students.studentsmanagement.constant.ResponseCodes;
import com.students.studentsmanagement.domain.po.Student;
import com.students.studentsmanagement.exception.BusinessException;
import com.students.studentsmanagement.service.StudentService;
import com.students.studentsmanagement.mapper.StudentMapper;
import com.students.studentsmanagement.utils.BaseResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

import static cn.hutool.poi.excel.cell.CellUtil.getCellValue;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {
    @Resource
    StudentMapper studentMapper;

    @Override
    public BaseResponse<Object> listStudents(String searchInfo, Integer order, Integer pageNum, Integer pageNo) {
        Page<Student> studentPage = new Page<>(pageNo, pageNum);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        if (!ObjUtil.isEmpty(searchInfo)) {
            studentQueryWrapper.like("name", searchInfo).or().like("stu_id", searchInfo);
        }
        if (!ObjUtil.isEmpty(order)) {
            switch (order) {
                case 0:
                    studentQueryWrapper.orderBy(true, true, "convert(name using gbk)");
                    break;
                case 1:
                    studentQueryWrapper.orderBy(true, false, "convert(name using gbk)");
                    break;
                case 2:
                    studentQueryWrapper.orderByAsc("stu_id");
                    break;
                case 3:
                    studentQueryWrapper.orderByDesc("stu_id");
                    break;
                default:
                    throw new BusinessException(ResponseCodes.PARAMS_ERROR, "请检查order参数的值！");
            }
        }
        return BaseResponse.success(studentMapper.selectPage(studentPage, studentQueryWrapper));
    }

    @Override
    public BaseResponse<Object> updateStudent(Long id, Long stuId, String name, Integer age, Integer gender, String birthday, String address, String phone, String email) {
        Student student = new Student();
        student.setId(id);
        setStudent(stuId, name, age, gender, birthday, address, phone, email, student);
        return BaseResponse.success(studentMapper.updateById(student));
    }

    private void setStudent(Long stuId, String name, Integer age, Integer gender, String birthday, String address, String phone, String email, Student student) {
        student.setStuId(stuId);
        student.setName(name);
        student.setAge(age);
        student.setGender(gender);
        student.setBirthday(birthday);
        student.setAddress(address);
        student.setPhone(phone);
        student.setEmail(email);
    }

    @Override
    public BaseResponse<Object> deleteStudent(Long id) {
        return BaseResponse.success(studentMapper.deleteById(id));
    }

    @Override
    public BaseResponse<Object> addStudent(Long stuId, String name, Integer age, Integer gender, String birthday, String address, String phone, String email) {
        Student student = new Student();
        setStudent(stuId, name, age, gender, birthday, address, phone, email, student);
        return BaseResponse.success(studentMapper.insert(student));
    }

    @Override
    public BaseResponse<Object> importStudents(MultipartFile excel) throws IOException {
        Workbook wb = new XSSFWorkbook(excel.getInputStream());
        if (ObjUtil.isEmpty(wb)) {
            throw new BusinessException(ResponseCodes.PARAMS_ERROR, "导入失败，请重试！");
        }
        Sheet sheet = wb.getSheetAt(0);
        Row head = sheet.getRow(0);
        Object[] headValues = new Object[head.getLastCellNum()];
        for (int cellNum = 0; cellNum < head.getLastCellNum(); cellNum++) {
            Cell cell = head.getCell(cellNum);
            Object value = getCellValue(cell);
            headValues[cellNum] = value;
        }
        String[] expectedHeaders = {"学号", "姓名", "年龄", "性别", "出生年月", "家庭住址", "电话", "E-mail"};
        for (int i = 0; i < expectedHeaders.length; i++) {
            if (!StrUtil.equals(headValues[i].toString(), expectedHeaders[i])) {
                throw new BusinessException(ResponseCodes.PARAMS_ERROR, "Excel表头设置错误！");
            }
        }
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            Object[] values = new Object[row.getLastCellNum()];
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                Object value = getCellValue(cell);
                values[cellNum] = value;
            }
            Student student = new Student();
            student.setStuId(Long.valueOf(values[0].toString()));
            student.setName(values[1].toString());
            student.setAge(Integer.valueOf(values[2].toString()));
            student.setGender(values[3].toString().equals("男")? 0 : 1);
            student.setBirthday(values[4].toString());
            student.setAddress(values[5].toString());
            student.setPhone(values[6].toString());
            student.setEmail(values[7].toString());
            studentMapper.insert(student);
        }
        return BaseResponse.success();
    }
}





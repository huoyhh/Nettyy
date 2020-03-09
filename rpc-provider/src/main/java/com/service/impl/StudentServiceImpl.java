package com.service.impl;

import com.entity.Student;
import com.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author CBeann
 * @create 2020-03-06 22:41
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public Student selectStudentById(Integer studentId) {
        Student student = new Student();
        student.setId(1);
        student.setName("CBeann");
        return student;
    }
}

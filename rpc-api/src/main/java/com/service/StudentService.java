package com.service;

import com.entity.Student;

/**
 * @author CBeann
 * @create 2020-03-06 22:12
 */
public interface StudentService {

    /*根据学生ID查询学生*/
    Student selectStudentById(Integer studentId);
}

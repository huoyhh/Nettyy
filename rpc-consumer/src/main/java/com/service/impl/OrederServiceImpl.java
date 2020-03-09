package com.service.impl;

import com.entity.Student;
import com.service.OrederService;
import org.springframework.stereotype.Service;

/**
 * @author CBeann
 * @create 2020-03-07 15:03
 */
@Service
public class OrederServiceImpl implements OrederService {


//    @Autowired
//    private StudentService studentService;


    @Override
    public Student getStudent(Integer studentId) {
        System.out.println("OrederServiceImpl......getStudent");
        return null;
    }
}

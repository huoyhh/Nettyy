package com.entity;

import lombok.Data;

/**
 * @author CBeann
 * @create 2020-03-06 22:11
 */
@Data
public class Student {

    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

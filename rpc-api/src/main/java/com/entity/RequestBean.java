package com.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CBeann
 * @create 2020-03-07 14:03
 */
@Data
public class RequestBean implements Serializable {

    private String requestClass;

    private String requestMethod;

    private Class atgsType;

    private Object args;
}

package com.entity.rpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CBeann
 * @create 2020-03-07 14:03
 */
@Data
public class ResponseBean implements Serializable {

    private Integer code;

    private Object data;
}

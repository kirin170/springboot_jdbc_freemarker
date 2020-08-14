package com.springboot.helloworld.entity;

import lombok.Data;

@Data
public class UserEntity {
//    id
    private int id;
//    标题
    private String title;
//    姓名
    private String name;
//    微信
    private String wxNumber;
}

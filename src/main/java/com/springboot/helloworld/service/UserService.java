package com.springboot.helloworld.service;

import com.springboot.helloworld.entity.PageEntity;
import com.springboot.helloworld.entity.UserEntity;

import java.util.Map;

public interface UserService {
    //    查询
    public Map<String, Object> selectUser(String keyword);
    //    分页
    public Map<String, Object> selectPageUser(int start, int size, String keyword);
    //    插入
    public String insertUser(UserEntity user);
    //    删除
    public String deleteUser(String uid);
    //    更新
    public String updateUser(UserEntity user);
}

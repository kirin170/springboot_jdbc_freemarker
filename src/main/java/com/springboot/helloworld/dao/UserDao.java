package com.springboot.helloworld.dao;

import com.springboot.helloworld.entity.PageEntity;
import com.springboot.helloworld.entity.UserEntity;

import java.util.List;

public interface UserDao {
    /***
     * 查询用户
     * @return
     */
    public List<UserEntity> selectUser(String keyword);
    /**
     * 分页查询
     */
    public PageEntity selectPageUser(int start, int size, String keyword);
    /**
     * 插入数据
     */
    public int insertUser(UserEntity user);

    /**
     * 删除数据
     */
    public int deleteUser(int uid);

    /**
     * 更新数据
     */
    public int updateUser(UserEntity user);
}

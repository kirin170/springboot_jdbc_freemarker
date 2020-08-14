package com.springboot.helloworld.service.impl;

import com.google.gson.JsonObject;
import com.springboot.helloworld.dao.UserDao;
import com.springboot.helloworld.entity.PageEntity;
import com.springboot.helloworld.entity.UserEntity;
import com.springboot.helloworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> selectUser(String keyword) {
        List<UserEntity> list = userDao.selectUser(keyword);
        Map<String, Object> map = new HashMap<>();
        if (list.size() == 0){
            map.put("userList", list);
            map.put("message", "没有查询到数据");
        }else{
            map.put("userList", list);
        }
        return map;
    }

    @Override
    public Map<String, Object> selectPageUser(int start, int size, String keyword) {
        PageEntity pageEntity = userDao.selectPageUser(start, size, keyword);
        List<UserEntity> list = pageEntity.getCurrentRecordList();

        Map<String, Object> map = new HashMap<>();
        map.put("userList", list);
        map.put("currentPage", pageEntity.getCurrentPage());
        map.put("totalPage", pageEntity.getTotalPage());
        map.put("keyword", pageEntity.getKeyword());
        map.put("pageUrl", "/index?");
        if (list.size() == 0){
            map.put("message", "暂时没有数据哦");
        }
        return map;
    }

    @Override
    public String insertUser(UserEntity user) {
        JsonObject result = new JsonObject();
        if (user.getName().isEmpty()) {
            result.addProperty("code", "-1");
            result.addProperty("message", "标题不能为空");
        }else{
            int insertResult = userDao.insertUser(user);
            if (insertResult >= 1){
                result.addProperty("code", "200");
                result.addProperty("message", "插入成功");
            }else{
                result.addProperty("code", "0");
                result.addProperty("message", "插入失败");
            }
        }
        return result.toString();
    }

    @Override
    public String updateUser(UserEntity user) {

        JsonObject result = new JsonObject();
        int updateResult = userDao.updateUser(user);
        if (updateResult >= 1){
            result.addProperty("code", "200");
            result.addProperty("message", "修改成功");
        }else{
            result.addProperty("code", "0");
            result.addProperty("message", "修改失败");
        }

        return result.toString();
    }

    @Override
    public String deleteUser(String uid) {
        int deleteResult = userDao.deleteUser(Integer.parseInt(uid));

        JsonObject result = new JsonObject();
        if (deleteResult >= 1){
            result.addProperty("code", "200");
            result.addProperty("message", "删除成功");
        }else{
            result.addProperty("code", "0");
            result.addProperty("message", "删除失败");
        }
        return result.toString();
    }
}

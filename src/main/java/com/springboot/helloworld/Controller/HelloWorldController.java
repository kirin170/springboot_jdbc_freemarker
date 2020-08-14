package com.springboot.helloworld.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springboot.helloworld.entity.UserEntity;
import com.springboot.helloworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloWorldController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/index")
    public ModelAndView index(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "page", defaultValue = "1") Integer page){

    //        return new ModelAndView("user/index", userService.selectUser(keyword));
        return new ModelAndView("user/index", userService.selectPageUser(page, 5, keyword));
    }

    //    插入
    @PostMapping(value = "/insertUser", consumes="application/json")
    public String insert(@RequestBody JsonObject object){
        UserEntity user = new Gson().fromJson(object, UserEntity.class);

        return userService.insertUser(user);
    }
    //    修改
    @PostMapping(value = "/updateUser", consumes = "application/json")
    public String update(@RequestBody JsonObject object){
        UserEntity user = new Gson().fromJson(object, UserEntity.class);

        return userService.updateUser(user);
    }
    //    删除
    @PostMapping(value = "/deleteUser", consumes = "application/json")
    public String delete(@RequestBody JsonObject object){
        String userId = object.get("uid").toString();
        return userService.deleteUser(userId);

    }
}

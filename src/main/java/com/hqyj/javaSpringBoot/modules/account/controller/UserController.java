package com.hqyj.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.account.service.UserService;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

/**
 * author  Jayoung
 * createDate  2020/8/20 0020 9:05
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 127.0.0.1:667/api/user   ---- post
     * {"userName":"admin","password":"111111"}
     */
    @PostMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }


    /**
     * 127.0.0.1:667/api/login   ---- post
     * {"userName":"admin","password":"111111"}
     */
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> getUserByUserNameAndPassword(@RequestBody User user){
        return userService.getUserByUserNameAndPassword(user);

    }


    /**
     * 127.0.0.1:667/api/users   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @PostMapping(value = "/users",consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo) {
        return userService.getUsersBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1:667/api/user   ---- put
     * {"userName":"adminnn","userImg":"/aaa.jpg","userId":"4"}
     */
    @PutMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }


    /**
     * 127.0.0.1:667/api/user/userId   ---- delete
     */
    @DeleteMapping("/user/{userId}")
    public Result<Object> deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 127.0.0.1:667/api/user/userId   ---- get
     */
    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable int userId) {
        return userService.getUserByUserId(userId);
    }



    //多个文件上传
    /**
     * 127.0.0.1:667/api/userImg   ---- post
     */
    @PostMapping(value = "userImg", consumes = "multipart/form-data")
    public Result<String> uploadUserImg(@RequestParam MultipartFile file) {
        return userService.uploadUserImg(file);
    }

    /**
     * 127.0.0.1/api/profile ---- put
     */
    @PutMapping(value = "/profile", consumes = "application/json")
    public Result<User> updateUserProfile(@RequestBody User user) {
        return userService.updateUserProfile(user);
    }
}

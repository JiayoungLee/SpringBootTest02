package com.hqyj.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.dao.UserDao;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.account.service.UserService;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import com.hqyj.javaSpringBoot.utils.MD5Util;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * author  Jayoung
 * createDate  2020/8/20 0020 9:02
 * version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result<User> insertUser(User user) {
        User userTmp = userDao.getUserByUserName(user.getUserName());
        if (userTmp!=null){
            return new Result<User>(Result.ResultStatus.FAILED.status,"User Name already exists!");
        }

        user.setCreateDate(LocalDateTime.now());
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        userDao.insertUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status,"insert success！");
    }

    @Override
    public Result<User> getUserByUserNameAndPassword(User user) {
        User userTmp = userDao.getUserByUserName(user.getUserName());
        if (userTmp != null && userTmp.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
            return  new Result<User>(Result.ResultStatus.SUCCESS.status,"Login Successfully!!!");
        }
        return  new Result<User>(Result.ResultStatus.FAILED.status,"UserName or Password ERROR!");
    }

    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                .orElse(Collections.emptyList()));
    }
}

package com.hqyj.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;

/**
 * author  Jayoung
 * createDate  2020/8/20 0020 9:01
 * version 1.0
 */
public interface UserService {

    Result insertUser(User user);

    Result<User> getUserByUserNameAndPassword(User user);

    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
}

package com.hqyj.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.config.ResourceConfigBean;
import com.hqyj.javaSpringBoot.modules.account.dao.UserDao;
import com.hqyj.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.account.service.UserService;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import com.hqyj.javaSpringBoot.utils.MD5Util;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Override
    @Transactional
    public Result<User> insertUser(User user) {
        User userTmp = userDao.getUserByUserName(user.getUserName());
        if (userTmp != null) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
        }


        user.setCreateDate(LocalDateTime.now());
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        userDao.insertUser(user);

        //要先插入之后才有userId可用
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            //遍历采用的是第二种方式：jdk8的新方式
//            for (Role role: roles){
//                userRoleDao.insertUserRole(user.getUserId(),role.getRoleId());
//            }

            roles.stream().forEach(item -> {
                userRoleDao.insertUserRole(user.getUserId(), item.getRoleId());
            });
        }

        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "insert success！");
    }

    //    Login
    @Override
    public Result<User> getUserByUserNameAndPassword(User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(user.getUserName(),
                        MD5Util.getMD5(user.getPassword()));
        usernamePasswordToken.setRememberMe(user.getRememberMe());

        try {
            subject.login(usernamePasswordToken);
            subject.checkRoles();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "UserName or Password is Error");
        }

        Session session = subject.getSession();
        session.setAttribute("user",(User)subject.getPrincipal());

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Login Successfully!!!");
    }

    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        User userTmp = userDao.getUserByUserName(user.getUserName());
        if (userTmp != null && userTmp.getUserId() != user.getUserId()) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
        }
        userDao.updateUser(user);


        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach(item -> {
                userRoleDao.insertUserRole(user.getUserId(), item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Update Success!!", user);
    }

    @Override
    @Transactional
    public Result<Object> deleteUser(int userId) {
        userDao.deleteUser(userId);
        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<>(Result.ResultStatus.SUCCESS.status, "Delete Success!!");
    }

    @Override
    public User getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
        if (file.isEmpty()) {
            return new Result<String>(
                    Result.ResultStatus.FAILED.status, "Please Select Img");
        }
        String relativePath = "";
        String destFilePath = "";
        try {
            String osName = System.getProperty("os.name");
            osName = osName.toLowerCase();
            if (osName.startsWith("win")) {
                destFilePath = resourceConfigBean.getLocationPathForWindows()
                        + file.getOriginalFilename();
            } else {
                destFilePath = resourceConfigBean.getLocationPathForLinux()
                        + file.getOriginalFilename();
            }

            relativePath = resourceConfigBean.getRelativePath()
                    + file.getOriginalFilename();

            File destFile = new File(destFilePath);
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(Result.ResultStatus.FAILED.status, "Upload Failed!!");
        }
        return new Result<String>(Result.ResultStatus.SUCCESS.status, "Upload Success!!", relativePath);
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        User userTmp = userDao.getUserByUserName(user.getUserName());
        if (userTmp != null && userTmp.getUserId() != user.getUserId()) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
        }
        userDao.updateUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Update Success!!", user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        Session session = subject.getSession();
        session.setAttribute("user",null);
    }
}

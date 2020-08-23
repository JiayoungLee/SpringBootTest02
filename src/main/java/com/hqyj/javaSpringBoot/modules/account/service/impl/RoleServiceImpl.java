package com.hqyj.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.dao.RoleDao;
import com.hqyj.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.account.service.RoleService;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * author  Jayoung
 * createDate  2020/8/21 0021 14:00
 * version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<Role> getRoles() {
        return Optional.ofNullable(roleDao.getRoles())
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public Result<Role> insertRole(Role role) {
        Role roleTmp = roleDao.getRoleByRoleName(role.getRoleName());
        if (roleTmp != null){
            return new Result<Role>(Result.ResultStatus.FAILED.status,"The Inserting Name Is Exist!");
        }

        roleDao.insertRole(role);
        return new Result<Role>(Result.ResultStatus.SUCCESS.status,"Insert Success");
    }

    @Override
    public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());

        return new PageInfo<Role>(Optional
                .ofNullable(roleDao.getRolesBySearchVo(searchVo))
                .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<Role> updateRole(Role role) {
        Role roleTmp = roleDao.getRoleByRoleName(role.getRoleName());
        if(roleTmp != null && roleTmp.getRoleId() != role.getRoleId()){
            return new Result<Role>(Result.ResultStatus.FAILED.status,"Updating RoleName Is Exist");
        }

        roleDao.updateRole(role);
        return new Result<Role>(Result.ResultStatus.SUCCESS.status,"Update Success!!",role);
    }

    @Override
    @Transactional
    public Result<Object> deleteRole(int roleId) {
        roleDao.deleteRole(roleId);
        userRoleDao.deleteUserRoleByRoleId(roleId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Delete Success!!");
    }

    @Override
    public Role getRoleByRoleId(int roleId) {
        return roleDao.getRoleByRoleId(roleId);
    }
}

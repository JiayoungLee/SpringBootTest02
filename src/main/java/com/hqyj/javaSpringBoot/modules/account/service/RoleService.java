package com.hqyj.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/21 0021 14:00
 * version 1.0
 */
public interface RoleService {
    List<Role> getRoles();

    Result insertRole(Role role);

    PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);

    Result<Role> updateRole(Role role);

    Result<Object> deleteRole(int roleId);

    Role getRoleByRoleId(int roleId);
}

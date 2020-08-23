package com.hqyj.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.account.entity.User;
import com.hqyj.javaSpringBoot.modules.account.service.RoleService;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.index.GeoIndexed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/21 0021 14:02
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /*
    *  http://localhost:667/api/roles   ---- get
    * */
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }


    /*
     *  http://localhost:667/api/role   ---- post
     *  {"roleName":"admin111"}
     * */
    @PostMapping(value = "/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Role> insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    /*
     *  http://localhost:667/api/roles   ---- post
     *  {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     * */
    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<Role> getRolesBySearchVo(@RequestBody SearchVo searchVo) {
        return roleService.getRolesBySearchVo(searchVo);
    }


    /**
     * 127.0.0.1:667/api/role   ---- put
     * {"roleName":"adminnnxxx","roleId":"5"}
     */
    @PutMapping(value = "/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Role> updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 127.0.0.1:667/api/role/roleId   ---- delete
     */
    @DeleteMapping("/role/{roleId}")
    public Result<Object> deleteRole(@PathVariable int roleId) {
        return roleService.deleteRole(roleId);
    }

    /**
     * 127.0.0.1:667/api/role/roleId   ---- get
     */
    @GetMapping("/role/{roleId}")
    public Role getRoleByRoleId(@PathVariable int roleId) {
        return roleService.getRoleByRoleId(roleId);
    }
}

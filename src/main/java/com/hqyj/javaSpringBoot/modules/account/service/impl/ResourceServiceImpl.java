package com.hqyj.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.dao.ResourceDao;
import com.hqyj.javaSpringBoot.modules.account.dao.RoleResourceDao;
import com.hqyj.javaSpringBoot.modules.account.entity.Resource;
import com.hqyj.javaSpringBoot.modules.account.entity.Role;
import com.hqyj.javaSpringBoot.modules.account.service.ResourceService;
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
 * createDate  2020/8/23 0023 17:55
 * version 1.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Override
    @Transactional
    public Result<Resource> insertResource(Resource resource) {
        Resource resourceTmp = resourceDao.getResourceByResourceName(resource.getResourceName());
        if (resourceTmp != null){
            return new Result<Resource>(Result.ResultStatus.FAILED.status,"Resource Name already exists!");
        }
        resourceDao.insertResource(resource);

        roleResourceDao.deleteRoleResourceByResourceId(resource.getResourceId());

        List<Role> roles = resource.getRoles();
        if (roles != null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(resource.getResourceId(),item.getRoleId());
            });
        }

        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,"Insert Success!");
    }

    @Override
    public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());

        return new PageInfo<Resource>(
                Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<Resource> updateResource(Resource resource) {
        Resource resourceTmp = resourceDao.getResourceByResourceName(resource.getResourceName());
        if (resourceTmp != null && resourceTmp.getResourceId() != resource.getResourceId()){
            new Result<Resource>(Result.ResultStatus.FAILED.status,"Resource Name already exists!");
        }

        resourceDao.updateResource(resource);

        roleResourceDao.deleteRoleResourceByResourceId(resource.getResourceId());

        List<Role> roles = resource.getRoles();
        if (roles != null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(resource.getResourceId(),item.getRoleId());
            });
        }
        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,"Update Success!!",resource);
    }

    @Override
    @Transactional
    public Result<Object> deleteResource(int resourceId) {
        resourceDao.deleteResource(resourceId);
        roleResourceDao.deleteRoleResourceByResourceId(resourceId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Delete Success!!");
    }

    @Override
    public Resource getResourceByResourceId(int resourceId) {
        return resourceDao.getResourceByResourceId(resourceId);
    }

    @Override
    public List<Resource> getResourcesByRoleId(int roleId) {
        return resourceDao.getResourcesByRoleId(roleId);
    }
}

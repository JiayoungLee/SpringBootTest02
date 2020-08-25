package com.hqyj.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.Resource;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/23 0023 17:54
 * version 1.0
 */
public interface ResourceService {

    Result insertResource(Resource resource);

    PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);

    Result<Resource> updateResource(Resource resource);

    Result<Object> deleteResource(int resourceId);

    Resource getResourceByResourceId(int resourceId);

    List<Resource> getResourcesByRoleId(int roleId);
}

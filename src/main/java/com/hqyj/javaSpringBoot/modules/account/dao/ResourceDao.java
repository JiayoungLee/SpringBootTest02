package com.hqyj.javaSpringBoot.modules.account.dao;

import com.hqyj.javaSpringBoot.modules.account.entity.Resource;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/23 0023 17:54
 * version 1.0
 */
@Repository
@Mapper
public interface ResourceDao {

    @Insert("insert into resource (resource_uri, resource_name, permission) " +
            "values(#{resourceUri}, #{resourceName}, #{permission})")
    @Options(useGeneratedKeys = true, keyColumn = "resource_id", keyProperty = "resourceId")
    void insertResource(Resource resource);

    @Select("select * from resource where resource_name = #{resourceName}")
    Resource getResourceByResourceName(String resourceName);

    @Select("<script>" +
            "select * from resource "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (resource_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by resource_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Resource> getResourcesBySearchVo(SearchVo searchVo);

    @Update("update resource set resource_uri = #{resourceUri}, resource_name = #{resourceName}" +
            ", permission = #{permission} where resource_id = #{resourceId}")
    void updateResource(Resource resource);

    @Delete("delete from resource where resource_id = #{resourceId}")
    void deleteResource(int resourceId);

    @Select("select * from resource where resource_id=#{resourceId}")
    @Results(id = "resourceResults", value = {@Result(column = "resource_id",property = "resourceId"),
            @Result(column = "resource_id",property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.hqyj.javaSpringBoot.modules.account.dao.RoleDao.getRolesByResourceId"))})
    Resource getResourceByResourceId(int resourceId);

    @Select("SELECT resource.resource_id, resource.resource_uri, resource.resource_name, " +
            "resource.permission FROM resource INNER JOIN role_resource ON resource.resource_id " +
            "= role_resource.resource_id where role_resource.role_id = #{roleId}")
    List<Resource> getResourcesByRoleId(int roleId);
}

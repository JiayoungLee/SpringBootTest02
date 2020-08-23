package com.hqyj.javaSpringBoot.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author  Jayoung
 * createDate  2020/8/23 0023 19:14
 * version 1.0
 */
@Repository
@Mapper
public interface RoleResourceDao {

    @Delete("delete from role_resource where resource_id=#{resourceId}")
    void deleteRoleResourceByResourceId(int resourceId);

    @Insert("insert into role_resource (resource_id, role_id) " +
            "values (#{resourceId}, #{roleId})")
    void insertRoleResource(@Param("resourceId") int resourceId, @Param("roleId") int roleId);
}

package com.hqyj.javaSpringBoot.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author  Jayoung
 * createDate  2020/8/21 0021 9:19
 * version 1.0
 */
@Repository
@Mapper
public interface UserRoleDao {

    @Delete("delete from user_role where user_id=#{userId}")
    void deleteUserRoleByUserId(int userId);

    @Insert("insert into user_role (user_id, role_id) " +
            "values (#{userId}, #{roleId})")
    void insertUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    @Delete("delete from user_role where role_id=#{roleId}")
    void deleteUserRoleByRoleId(int roleId);
}

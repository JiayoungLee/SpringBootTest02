package com.hqyj.javaSpringBoot.modules.test.dao;

import com.hqyj.javaSpringBoot.modules.test.entity.Country;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/11 0011 16:35
 * version 1.0
 */

@Repository
@Mapper
public interface CountryDao {

    @Select("select * from m_country where country_id=#{countryId} ")
    @Results(id = "countryResults",value = {
            @Result(column = "country_id",property = "countryId"),
            @Result(column = "country_id",property = "cities",
                    javaType = List.class,
                    many = @Many(select = "com.hqyj.javaSpringBoot.modules.test.dao.CityDao.getCountryByCountryId"))
    })
    Country getCountryByCountryId(int countryId);


    @Select("select * from m_country where country_name=#{countryName} ")
    @ResultMap(value = "countryResults")
    Country getCountryByCountryName(String countryName);
}

package com.hqyj.javaSpringBoot.modules.test.service;

import com.hqyj.javaSpringBoot.modules.test.entity.Country;

/**
 * author  Jayoung
 * createDate  2020/8/11 0011 16:31
 * version 1.0
 */
public interface CountryService {
    Country getCountryByCountryId(int countryId);

    Country getCountryByCountryName(String countryName);

    Country mograteCountryByRedis(int countryId);
}

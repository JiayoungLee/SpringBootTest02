package com.hqyj.javaSpringBoot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import com.hqyj.javaSpringBoot.modules.test.entity.City;
import com.hqyj.javaSpringBoot.modules.test.entity.Country;
import com.hqyj.javaSpringBoot.modules.test.service.CityService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/11 0011 16:33
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private CityService cityService;


    /*
     * http://localhost:667/api/cities/countryId  -----get
     * */
    @GetMapping("/cities/{countryId}")
    public List<City> getCitiesByCountryId(@PathVariable int countryId){
        return cityService.getCountryByCountryId(countryId);
    }


    /*
     * http://localhost:667/api/cities/522  -----post
     * {"currentPage":"1","pageSize":"5"}
     * */
    @PostMapping(value = "/cities/{countryId}",consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@PathVariable int countryId, @RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(countryId,searchVo);
    }

    /*
     * http://localhost:667/api/cities  -----post
     * {"currentPage":"1","pageSize":"5","keyWord":"Sh","orderBy":"city_name","sort":"desc"}
     * */
    @PostMapping(value = "/cities",consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(searchVo);
    }

    /*
     * http://localhost:667/api/city    -----post
     * {"cityName":"test1","localCityName":"freeCityName","countryId":"522"}
     * application/x-www-form-urlencoded    ModelAttribute
     * */
    @PostMapping(value = "/city",consumes = "application/json")
    public Result<City> insertCity(@RequestBody City city) {
       return cityService.insertCity(city);
    }

    /*
     * http://localhost:667/api/city    -----put
     * {"cityId":"2258","cityName":"aabb"}
     * application/x-www-form-urlencoded    ModelAttribute
     * */
    @PutMapping(value = "/city",consumes = "application/x-www-form-urlencoded")
    public Result<City> updateCity(@ModelAttribute City city) {
        return cityService.updateCity(city);
    }

    /**
     * http://localhost:667/api/city/cityId    -----delete
     */
    @DeleteMapping(value = "/city/{cityId}")
    public Result<Object> deleteCity(@PathVariable int cityId) {
        return cityService.deleteCity(cityId);
    }
}

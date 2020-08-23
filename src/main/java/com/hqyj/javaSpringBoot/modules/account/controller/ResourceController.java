package com.hqyj.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.javaSpringBoot.modules.account.entity.Resource;
import com.hqyj.javaSpringBoot.modules.account.service.ResourceService;
import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * author  Jayoung
 * createDate  2020/8/23 0023 17:49
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 127.0.0.1:667/api/resource   ---- post
     * {"resourceUri":"www.1.com","resourceName":"资源1","permission":"permission01"}
     */
    @PostMapping(value = "/resource", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Resource> insertResource(@RequestBody Resource resource) {
        return resourceService.insertResource(resource);
    }

    /**
     * 127.0.0.1:667/api/resources   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"资源"}
     */
    @PostMapping(value = "/resources", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<Resource> getResourcesBySearchVo(@RequestBody SearchVo searchVo) {
        return resourceService.getResourcesBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1:667/api/resource   ---- put
     * {"resourceName":"资源008","resourceUri":"www.08.com","permission":"permission008","resourceId":"8"}
     */
    @PutMapping(value = "resource", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Resource> updateResource(@RequestBody Resource resource) {
        return resourceService.updateResource(resource);
    }

    /**
     * 127.0.0.1:667/api/resource/resourceId   ---- delete
     */
    @DeleteMapping("/resource/{resourceId}")
    public Result<Object> deleteResource(@PathVariable int resourceId) {
        return resourceService.deleteResource(resourceId);
    }

    /**
     * 127.0.0.1:667/api/resource/resourceId   ---- get
     */
    @GetMapping("/resource/{resourceId}")
    public Resource getResourceByResourceId(@PathVariable int resourceId) {
        return resourceService.getResourceByResourceId(resourceId);
    }
}

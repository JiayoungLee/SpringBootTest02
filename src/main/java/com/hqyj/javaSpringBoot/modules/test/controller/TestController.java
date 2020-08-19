package com.hqyj.javaSpringBoot.modules.test.controller;

import com.hqyj.javaSpringBoot.modules.test.entity.City;
import com.hqyj.javaSpringBoot.modules.test.entity.Country;
import com.hqyj.javaSpringBoot.modules.test.service.CityService;
import com.hqyj.javaSpringBoot.modules.test.service.CountryService;
import com.hqyj.javaSpringBoot.modules.test.vo.ApplicationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author  Jayoung
 * createDate  2020/8/10 0010 10:40
 * version 1.0
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${server.port}")
    private int port;
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

    @Autowired
    private ApplicationTest applicationTest;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;


    /*
     * 文件下载 方式1 ---常用
     * http://localhost:667/test/file   ----get
     * */
    @GetMapping(value = "/file")
    public ResponseEntity<Resource> downLoadFile(@RequestParam String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource(Paths.get("F:\\uploadTestForCode\\" + fileName).toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                String.format("attachment; filename=\"%s\"", resource.getFilename()))
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *  文件下载 2
     * 将文件以BufferedInputStream的方式读取byte[]里，然后用OutputStream.write输出文件
     * */
    @GetMapping("/file2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam String fileName) {
        String filePath = "F:/uploadTestForCode" + File.separator + fileName;
        File downloadFile = new File(filePath);

        response.setContentType("application/octet-stream");
        response.setContentLength((int) downloadFile.length());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", fileName));
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            fileInputStream = new FileInputStream(downloadFile);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream outputStream = response.getOutputStream();
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }
            }catch (Exception e2){
                LOGGER.debug(e2.getMessage());
                e2.printStackTrace();
            }
        }
    }


    //多个文件上传
    @PostMapping(value = "files", consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files,
                              RedirectAttributes redirectAttributes) {

        boolean isEmpty = true;
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                String destFilePath = "F:\\uploadTestForCode\\" + file.getOriginalFilename();
                File destFile = new File(destFilePath);
                file.transferTo(destFile);
                isEmpty = false;
            }
            if (isEmpty) {
                //如果没有选择文件
                redirectAttributes.addFlashAttribute("message", "Please select file");
            } else {
                //上传成功
                redirectAttributes.addFlashAttribute("message", "Upload file success");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //如果上传失败
            redirectAttributes.addFlashAttribute("message", "Upload file failed");
        }
        return "redirect:/test/index";
    }

    //单个文件上传
    //针对form表单上传文件consumes = "multipart/form-data"
    //@RequestParam MultipartFile file
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public String uploadFile(@RequestParam MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        //如果没有选择文件
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select file！！！");
            return "redirect:/test/index";
        }
        try {
            String destFilePath = "F:\\uploadTestForCode\\" + file.getOriginalFilename();
            File destFile = new File(destFilePath);
            file.transferTo(destFile);
            redirectAttributes.addFlashAttribute("message", "Upload file success！！！");
        } catch (IOException e) {
            e.printStackTrace();
            //如果上传失败
            redirectAttributes.addFlashAttribute("message", "Upload file failed！！！");
        }
        //上传成功
        return "redirect:/test/index";
    }


    /*
     * http://localhost:667/test/index   ----get
     * */
    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap) {
        int countryId = 522;
        List<City> cities = cityService.getCountryByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());

        Country country = countryService.getCountryByCountryId(countryId);

        modelMap.addAttribute("thymeleafTitle", "Thymeleaf text");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkBox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("shopLogo", "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
        modelMap.addAttribute("shopLogo", "/upload/1111.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");

        modelMap.addAttribute("template", "test/index");
        //返回外层的碎片组装器
        return "index";
    }

    /*
     * http://localhost:667/test/index2   ----get
     * */
    @GetMapping("/index2")
    public String testIndex2Page(ModelMap modelMap) {
        modelMap.addAttribute("template", "test/index2");
        //返回外层的碎片组装器
        return "index";
    }

    /*
     * http://localhost:8085/test/logTest   ----get
     * */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest() {
        LOGGER.trace("This is trace log");
        LOGGER.debug("This is debug log");
        LOGGER.info("This is info log");
        LOGGER.warn("This is warn log");
        LOGGER.error("This is error log");

        return "This is log test";
    }


    /*
     * http://localhost:8085/test/testProperties   ----get
     * */
    @GetMapping("testProperties")
    @ResponseBody
    public String testProperties() {
        StringBuffer sb = new StringBuffer();

        return sb.append(age).append("---").append(name).append("---")
                .append(desc).append("---").append(random).append("<br>")
                .append(applicationTest.getName()).append("---").append(applicationTest.getAge()).append("---")
                .append(applicationTest.getDesc()).append("---").append(applicationTest.getRandom()).toString();
    }


    /*
     * 127.0.0.1:8085/test/testDesc   ----get
     * */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(HttpServletRequest request, @RequestParam(value = "paramKey") String paramValue) {
        String paramValue2 = request.getParameter("paramKey");
        return "This is test module desc." + paramValue + "==" + paramValue2;
    }


}

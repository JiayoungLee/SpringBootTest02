package com.hqyj.javaSpringBoot.modules.test.controller;

import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import com.hqyj.javaSpringBoot.modules.test.entity.Card;
import com.hqyj.javaSpringBoot.modules.test.entity.Student;
import com.hqyj.javaSpringBoot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/12 0012 20:04
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * http://localhost:667/api/student   -----post
     * {"studentName":"JayoungLee","studentCard":{"cardId":"1"}}
     */
    @PostMapping(value = "/student", consumes = "application/json")
    public Result<Student> insertCard(@RequestBody Student student) {
        return studentService.insertStudent(student);
    }


    /**
     * http://localhost:667/api/student/studentId  -----get
     */
    @GetMapping("/student/{studentId}")
    public Student getStudentByStudentId(@PathVariable int studentId) {

        return studentService.getStudentByStudentId(studentId);
    }


    /**
     * http://localhost:667/api/students   -----post
     * {"currentPage":"1","pageSize":"2","keyWord":"Ja","orderBy":"studentName","sort":"desc"}
     */
    @PostMapping(value = "/students",consumes = "application/json")
    public Page<Student> getStudentsBySearchVo(@RequestBody SearchVo searchVo) {
        return studentService.getStudentsBySearchVo(searchVo);
    }


    /**
     * http://localhost:667/api/students?studentName=JayoungLee   -----get
     */
    @GetMapping("/students")
    public List<Student> getStudentByStudentName(@RequestParam String studentName,
                                                 @RequestParam(required = false,defaultValue = "0") Integer cardId) {
        return studentService.getStudentsByStudentName(studentName,cardId);
    }


}

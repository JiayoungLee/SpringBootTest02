package com.hqyj.javaSpringBoot.modules.test.service.impl;

import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.common.vo.SearchVo;
import com.hqyj.javaSpringBoot.modules.test.entity.Student;
import com.hqyj.javaSpringBoot.modules.test.repository.StudentRepository;
import com.hqyj.javaSpringBoot.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * author  Jayoung
 * createDate  2020/8/12 0012 20:01
 * version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Result<Student> insertStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        studentRepository.saveAndFlush(student);
        return new Result<>(Result.ResultStatus.SUCCESS.status, "Insert success.", student);
    }

    @Override
    public Student getStudentByStudentId(int studentId) {
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Page<Student> getStudentsBySearchVo(SearchVo searchVo) {
        String orderBy = StringUtils.isBlank(searchVo.getOrderBy()) ?
                //oederBy得用entity对应的属性名，而非数据库字段，这一点与Mybatis不同
                "studentId" : searchVo.getOrderBy();
        //direction 排序是desc or asc
        Sort.Direction direction = StringUtils.isBlank(searchVo.getSort()) || "desc".equalsIgnoreCase(searchVo.getSort())  ?     //equalsIgnoreCase()忽略大小写后比较
                Sort.Direction.DESC : Sort.Direction.ASC;
        //sort   包含direction 和 oderby
        Sort sort = new Sort(direction, orderBy);
        //分页查询 Pageable
        Pageable pageable = PageRequest.of(
                //Pageable的起始页是从0开始的，所以当前页得 -1
                searchVo.getCurrentPage()-1,searchVo.getPageSize(),sort);

        //条件查询Example
        //这里是按照studentName作为查询条件
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("studentName",match -> match.contains()).
                withIgnorePaths("studentId");

        Student student = new Student();
        student.setStudentName(searchVo.getKeyWord());
        Example<Student> example = Example.of(student,matcher);

        return studentRepository.findAll(example,pageable);
    }

    @Override
    public List<Student> getStudentsByStudentName(String studentName,int cardId) {
        if (cardId>0){
            return studentRepository.getStudentsByParams(studentName,cardId);
        }
//        return Optional
//                .ofNullable(studentRepository.findByStudentName(studentName))
//                .orElse(Collections.emptyList());

//        return Optional
//                .ofNullable(studentRepository.findByStudentNameLike(String.format("%s%s%s","%",studentName,"%")))
//                .orElse(Collections.emptyList());

        return Optional
                .ofNullable(studentRepository.findTop2ByStudentNameLike(String.format("%s%s%s","%",studentName,"%")))
                .orElse(Collections.emptyList());
    }
}

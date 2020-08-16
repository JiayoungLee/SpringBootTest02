package com.hqyj.javaSpringBoot.modules.test.repository;

import com.hqyj.javaSpringBoot.modules.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/12 0012 20:00
 * version 1.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    //按照jpa规则写的接口  开始
    //通过名字查找
    List<Student> findByStudentName(String studentName);

    //通过名字模糊查找
    List<Student> findByStudentNameLike(String studentName);

    //通过名字模糊查找后的前两个
    List<Student> findTop2ByStudentNameLike(String studentName);
    //按照jpa规则写的接口  结束


    //自定义   开始  （增删改查都用 @Query注解）
    //getStudentsByParams 形式1
//    @Query(value = "select s from Student s where s.studentName  = ?1 and s.studentCard.cardId = ?2")
//    List<Student> getStudentsByParams(String studentName,int cardId);

    //getStudentsByParams 形式2
//    @Query(value = "select s from Student s where s.studentName = :studentName " +
//            "and s.studentCard.cardId = :cardId")
//    List<Student> getStudentsByParams(@Param("studentName") String studentName,
//                                      @Param("cardId") int cardId);


    //getStudentsByParams 形式3 --  sql语句形式
    @Query(nativeQuery = true, value = "select * from h_student where student_name = :studentName " +
            "and card_id = :cardId")
    List<Student> getStudentsByParams(@Param("studentName") String studentName,
                                      @Param("cardId") int cardId);

    //自定义   开始

}

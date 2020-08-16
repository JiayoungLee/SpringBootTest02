package com.hqyj.javaSpringBoot.modules.test.repository;

import com.hqyj.javaSpringBoot.modules.test.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author  Jayoung
 * createDate  2020/8/12 0012 19:10
 * version 1.0
 */
@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
}

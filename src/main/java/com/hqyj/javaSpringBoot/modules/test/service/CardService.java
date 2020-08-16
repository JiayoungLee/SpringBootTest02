package com.hqyj.javaSpringBoot.modules.test.service;

import com.hqyj.javaSpringBoot.modules.common.vo.Result;
import com.hqyj.javaSpringBoot.modules.test.entity.Card;

/**
 * author  Jayoung
 * createDate  2020/8/12 0012 19:15
 * version 1.0
 */
public interface CardService {
    Result<Card> insertCard(Card card);
}

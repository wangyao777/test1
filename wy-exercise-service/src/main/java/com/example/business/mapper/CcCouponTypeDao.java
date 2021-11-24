package com.example.business.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CcCouponTypeDao {

    String selectCouponCheck(Map map);
}

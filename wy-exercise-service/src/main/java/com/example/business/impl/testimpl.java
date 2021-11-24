package com.example.business.impl;



import com.example.business.Itest;
import com.example.business.mapper.CcCouponTypeDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class testimpl implements Itest {

    @Resource
    private CcCouponTypeDao ccCouponTypeDao;

    public String test() {
        Map map = new HashMap();
        map.put("couponType","CC23");
        map.put("couponClass","14");
       String type = ccCouponTypeDao.selectCouponCheck(map);
       return type;
    }
}

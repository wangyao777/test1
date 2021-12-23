package com.example.business;

import com.example.business.model.CouponFileSendModel;
import org.apache.coyote.Response;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface Itest {

    String test();

    List fileRead();

    Response fileCount();

    Map redisTest();

    void testDemo(CouponFileSendModel couponFileSendModel);
}

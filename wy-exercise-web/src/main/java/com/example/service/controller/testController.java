package com.example.service.controller;


import com.example.business.Itest;
import com.example.business.model.CouponFileSendModel;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class testController {

    @Resource
    private Itest testimpl;

    @PostMapping("/test")
    public Map test(){
        Map map = new HashMap();
        map.put("test","test");
        map.put("type",testimpl.test());
        return map;
    }

    @PostMapping("/file")
    public List fileRead(){
        List list = new ArrayList();
        List<CouponFileSendModel> i = testimpl.fileRead();
        return i;
    }
    @PostMapping("/fileCount")
    public Map fileCount() {
        List list = new ArrayList();
        Response response = new Response();
        long i = 0;
        response = testimpl.fileCount();
        Map map = new HashMap();
        map.put("message",response.getMessage());
        return map;
    }
    @PostMapping("/redisTest")
    public Map redisTest() {
        //List list = new ArrayList();
        //Map map = new HashMap();
        long i = 0;
        Map map = testimpl.redisTest();
        return map;
    }

    @PostMapping("/testDemo")
    public void testDemo() {
        //List list = new ArrayList();
        //Map map = new HashMap();
        /*long i = 0;*/
        CouponFileSendModel couponFileSendModel = new CouponFileSendModel();
        couponFileSendModel.setPhone("11111111111");
        testimpl.testDemo(couponFileSendModel);
        /*return map;*/
    }
}

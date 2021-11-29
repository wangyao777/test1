package com.example.service.controller;


import com.example.business.Itest;
import com.example.business.model.CouponFileSendModel;
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
    public int fileCount(){
        List list = new ArrayList();
        int i = testimpl.fileCount();
        return i;
    }
}

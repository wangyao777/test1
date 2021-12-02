package com.example.business.impl;



import com.example.business.Itest;
import com.example.business.mapper.CcCouponTypeDao;
import com.example.business.model.CouponFileSendModel;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class testimpl implements Itest {

    @Resource
    private CcCouponTypeDao ccCouponTypeDao;

    @Value("${cc.batch.sendPath}")
    private String filePath;

    @Resource
    private com.example.business.util.fileUtil fileUtil;


    public String test() {
        Map map = new HashMap();
        map.put("couponType","CC23");
        map.put("couponClass","14");
       String type = ccCouponTypeDao.selectCouponCheck(map);
       return type;
    }

    public List fileRead() {
        List<CouponFileSendModel> list = fileUtil.fileContentXslx(filePath);
        System.out.println(list);
        return list;
    }

    public Response fileCount()  {
        long count = 0;
        Response response = new Response();
        response = fileUtil.countFileRowXslx(filePath);
        System.out.println(count);
        return response;
    }
}

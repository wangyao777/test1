package com.example.business.impl;


import com.example.business.Itest;
import com.example.business.common.AsyncService;
import com.example.business.mapper.CcCouponTypeDao;
import com.example.business.model.CouponFileSendModel;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class testimpl implements Itest {

    @Resource
    private CcCouponTypeDao ccCouponTypeDao;

    @Value("${cc.batch.sendPath}")
    private String filePath;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private com.example.business.util.fileUtil fileUtil;

    @Autowired
    private AsyncService asyncService;

    /*@Resource
    private ApplicationContext applicationContext;*/


    public String test() {
        Map map = new HashMap();
        map.put("couponType", "CC23");
        map.put("couponClass", "14");
        String type = ccCouponTypeDao.selectCouponCheck(map);
        return type;
    }

    public List fileRead() {
        List<CouponFileSendModel> list = fileUtil.fileContentXslx(filePath);
        System.out.println(list);
        return list;
    }

    public Response fileCount() {
        long count = 0;
        Response response = new Response();
        response = fileUtil.countFileRowXslx(filePath);
        System.out.println(count);
        return response;
    }
    public Map redisTest() {
        //testimpl asyncService = applicationContext.getBean(testimpl.class);
        redisTemplate.opsForValue().set("test", "test",4000, TimeUnit.MILLISECONDS);
        //redisTemplate.delete("test");
        asyncService.testAsync();
        //System.out.println(future);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("param", redisTemplate.opsForValue().get("test"));

        return map;
    }

    @Async
    public Future testAsync() {
        String para = "异步调用成功";
        try {
            Thread.sleep(20000);
            System.out.println(para);
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* return para;*/
        return new AsyncResult(para);
    }

    public static void main(String[] args) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        Jedis jedis = new Jedis("localhost");
        System.out.println("redis链接成功！");
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        redisTemplate.opsForValue().set("test","test");
        redisTemplate.opsForValue().get("test");
        System.out.println("获取缓存数据-----" + redisTemplate.opsForValue().get("test"));
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表为: " + list.get(i));
        }
    }
    public void testDemo(CouponFileSendModel couponFileSendModel){

        check(couponFileSendModel);
        String phone = couponFileSendModel.getPhone();
        System.out.println(phone);
    }

    void check(CouponFileSendModel couponFileSendModel){
        couponFileSendModel.setPhone("18711111111");
    }
}

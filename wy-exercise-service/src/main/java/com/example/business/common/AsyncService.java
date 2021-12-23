package com.example.business.common;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.concurrent.Future;


@Service
@EnableAsync
public class AsyncService {

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

    @SneakyThrows
    public static void main(String[] args) {
        Class<?> demo = null;
        Object object = null;
        try {
            demo = Class.forName("com.example.business.model.CouponFileSendModel");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            object = demo.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Method method1 = object.getClass().getMethod("setPhone",String.class);
            method1.invoke(object, "18111111111");
            Method method = object.getClass().getMethod("getPhone");
            System.out.println(method.invoke(object));
            method.getDefaultValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

package com.example.business;

import org.apache.coyote.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Itest {

    String test();

    List fileRead();

    Response fileCount();
}

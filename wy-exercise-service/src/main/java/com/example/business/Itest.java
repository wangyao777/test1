package com.example.business;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Itest {

    String test();

    List fileRead();

    int fileCount();
}

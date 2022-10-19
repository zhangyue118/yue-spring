package com.yue;

import com.spring.YueApplicationContext;
import com.yue.service.UserService;

public class Test {

    public static void main(String[] args) {

        YueApplicationContext applicationContext = new YueApplicationContext(AppConfig.class);

        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
    }
}

package com.yue;

import com.spring.YueApplicationContext;
import com.yue.service.OrderService;
import com.yue.service.UserService;
import com.yue.service.UserServiceInterface;

public class Test {

    public static void main(String[] args) {

        YueApplicationContext applicationContext = new YueApplicationContext(AppConfig.class);

        UserServiceInterface userService = (UserServiceInterface) applicationContext.getBean("userService");
        System.out.println(userService);
        userService.test();

        //UserService userService2 = (UserService) applicationContext.getBean("userService");
        //System.out.println(userService2);
        //userService2.test();
        //
        //OrderService orderService = (OrderService) applicationContext.getBean("orderService");
        //System.out.println(orderService);
        //orderService.test();
        //
        //OrderService orderService2 = (OrderService) applicationContext.getBean("orderService");
        //System.out.println(orderService2);
        //orderService2.test();
    }
}

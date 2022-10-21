package com.yue.service;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.InitializingBean;

@Component
public class UserService implements InitializingBean, UserServiceInterface, BeanNameAware {

    @Autowired
    private OrderService orderService;

    @YueValue("DNF")
    private String test;

    private String beanName;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }

    @Override
    public void test() {
        System.out.println("BeanNameAware-"+beanName);
        System.out.println(test);
        orderService.test();
    }

    @Override
    public void setBeanName(String name) {
        beanName=name;
    }
}

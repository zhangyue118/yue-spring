package com.yue.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: zy
 * @date: 2022/10/21 15:08
 * @description:
 */
@Component
public class YueBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println(beanName);
        if("userService".equals(beanName)){
            Object proxyInstance = Proxy.newProxyInstance(YueBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println(beanName+"--切面逻辑");
                    return method.invoke(bean);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}

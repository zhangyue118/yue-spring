package com.yue.service;

import com.spring.BeanNameAware;
import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: zy
 * @date: 2022/10/21 15:08
 * @description:
 */
@Component
public class YueValueBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(YueValue.class)) {
                field.setAccessible(true);
                try {
                    field.set(bean, field.getAnnotation(YueValue.class).value());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        //BeanPostProcessor方式回调
        if (BeanNameAware.class.isAssignableFrom(bean.getClass())) {
            ((BeanNameAware)bean).setBeanName(beanName);
        }
        return bean;
    }
}

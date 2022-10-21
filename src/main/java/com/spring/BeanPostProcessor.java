package com.spring;

/**
 * @author: zy
 * @date: 2022/10/21 13:48
 * @description:
 */
public interface BeanPostProcessor {

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
}

package com.spring;

/**
 * @author: zy
 * @date: 2022/10/21 11:12
 * @description:
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}

package com.spring;

/**
 * @author: zy
 * @date: 2022/10/20 10:35
 * @description:
 */
public class BeanDefinition {

    private String scope;

    private Boolean islazy;

    private Class type;

    public BeanDefinition(String scope, Boolean lazyInit, Class type) {
        this.scope = scope;
        this.islazy = lazyInit;
        this.type = type;
    }

    public BeanDefinition() {
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getIslazy() {
        return islazy;
    }

    public void setIslazy(Boolean islazy) {
        this.islazy = islazy;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}

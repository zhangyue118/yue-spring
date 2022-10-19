package com.spring;

import com.yue.AppConfig;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;

public class YueApplicationContext {

    private Class configClass;

    public YueApplicationContext(Class configClass) {
        this.configClass=configClass;

        //扫描
        if(configClass.isAnnotationPresent(ComponentScan.class)){
            ComponentScan annotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String value = annotation.value();
            value=value.replace(".", "/");
            //System.out.println(value);
            ClassLoader classLoader = YueApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(value);
            File file = new File(resource.getFile());
            if(file.isDirectory()){
                for (File s : file.listFiles()) {
                    //System.out.println(s.getAbsolutePath());
                    String substring = s.getAbsolutePath().substring(s.getAbsolutePath().indexOf("com"), s.getAbsolutePath().indexOf(".class"));
                    substring=substring.replace("\\", ".");
                    //System.out.println(substring);
                    Class<?> aClass=null;
                    try {
                        aClass = classLoader.loadClass(substring);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(aClass.isAnnotationPresent(Component.class)){

                    }
                }
            }
        }
    }

    public Object getBean(String userService) {
        return null;
    }
}

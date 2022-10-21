package com.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YueApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap=new HashMap<>();
    private Map<String, Object> objectMap=new HashMap<>();
    private List<BeanPostProcessor> ltBeanPostProcessor=new ArrayList<>();

    public YueApplicationContext(Class configClass) {
        this.configClass=configClass;

        //扫描
        scan(configClass);

        beanDefinitionMap.forEach((k,v)->{
            objectMap.put(k,createBean(k,v));

        });

    }

    private Object createBean(String beanName, BeanDefinition v) {
        Object o=null;
        Class type = v.getType();
        try {
            o = type.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //自动注入
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                String beanName1 = Introspector.decapitalize(field.getType().getSimpleName());
                if (getBean(beanName1)!=null) {
                    Object o1 = getBean(beanName1);
                    try {
                        field.set(o, o1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else {
                    Object o1 = createBean(beanName1, beanDefinitionMap.get(beanName1));
                    try {
                        field.set(o, o1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //createBean方式回调
        if (BeanNameAware.class.isAssignableFrom(type)) {
            ((BeanNameAware)o).setBeanName(beanName);
        }

        //初始化前
        for (BeanPostProcessor beanPostProcessor : ltBeanPostProcessor) {
            o = beanPostProcessor.postProcessBeforeInitialization(o, beanName);
        }

        //初始化
        if (o instanceof InitializingBean) {
            try {
                ((InitializingBean)o).afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //try {
            //    Method afterPropertiesSet = type.getMethod("afterPropertiesSet");
            //    afterPropertiesSet.invoke(o);
            //} catch (NoSuchMethodException e) {
            //    e.printStackTrace();
            //} catch (IllegalAccessException e) {
            //    e.printStackTrace();
            //} catch (InvocationTargetException e) {
            //    e.printStackTrace();
            //}
        }

        ////初始化后
        for (BeanPostProcessor beanPostProcessor : ltBeanPostProcessor) {
            o = beanPostProcessor.postProcessAfterInitialization(o, beanName);
        }

        return o;
    }

    private void scan(Class configClass) {
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

                        if(aClass.isAnnotationPresent(Component.class)){

                            if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) aClass.getConstructor().newInstance();
                                ltBeanPostProcessor.add(beanPostProcessor);
                            }else {
                                String beanName=aClass.getAnnotation(Component.class).value();
                                if("".equals(beanName)){
                                    beanName= Introspector.decapitalize(aClass.getSimpleName());
                                }

                                BeanDefinition beanDefinition=new BeanDefinition(aClass.isAnnotationPresent(Scope.class)?aClass.getAnnotation(Scope.class).value():"singleton", aClass.isAnnotationPresent(Lazy.class)?aClass.getAnnotation(Lazy.class).value():false, aClass);
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition==null){

        }
        if("singleton".equals(beanDefinition.getScope())){
            return objectMap.get(beanName);
        }else {
            return createBean(beanName, beanDefinition);
        }
    }
}

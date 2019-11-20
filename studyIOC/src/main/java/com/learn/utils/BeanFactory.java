package com.learn.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import sun.plugin.com.BeanClass;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanFactory {

    Map<String,Object> map = new HashMap();

    public BeanFactory(String xml){
        parseXml(xml);
    }

    public void parseXml(String xmlPath){
        File file = new File(this.getClass().getResource("/").getPath() + "//" + xmlPath);

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();

            //判断是ByType还是ByName
            Attribute loadTypeAttribute = root.attribute("default");
            boolean flag = loadTypeAttribute != null;

            for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
                Element childElement = it.next();

                if(childElement.getName().equalsIgnoreCase("bean")){
                    /**
                     * setup1、实例化对象
                     */
                    Attribute attributeId = childElement.attribute("id");
                    String beanName = attributeId.getValue();
                    Attribute attributeClass = childElement.attribute("class");
                    String beanClassStr = attributeClass.getValue();

                    try {
                        Class<?> beanClass = Class.forName(beanClassStr);

                        /**
                         * 维护依赖关系
                         * 看这个对象有没有依赖（判断是否有property。或者判断类是否有属性）
                         * 如果有则注入
                         */
                        Object o = null;
                        //这里是判断bean下面没有没有字标签，如果没有子标签就去判断ByType属性
                        for (Iterator<Element> itSecond = childElement.elementIterator(); itSecond.hasNext();) {
                            Element childBeanElement = itSecond.next();
                            //判断是<property/>标签还是<constructor/>标签
                            if(childBeanElement.getName().equalsIgnoreCase("property")){
                                Attribute attributeProperTypeName = childBeanElement.attribute("name");
                                String properTypeName = attributeProperTypeName.getValue();
                                Attribute attributeProperTypeRef = childBeanElement.attribute("ref");
                                String properTypeRef = attributeProperTypeRef.getValue();
                                Object injetObject = this.getBean(properTypeRef);

                                o = beanClass.newInstance();
                                Field declaredField = beanClass.getDeclaredField(properTypeName);
                                declaredField.setAccessible(true);
                                declaredField.set(o,injetObject);

                            }else if(childBeanElement.getName().equalsIgnoreCase("constructor")){
                                //<constructor/>
                                String ref = childBeanElement.attribute("ref").getValue();

                                Object injetObject = this.getBean(ref);
                                //这里必须用父类接口
                                //原因Map里存放的是UserDaoImpl,但是构造函数里要的UserDao
                                Constructor<?> constructor = beanClass.getConstructor(injetObject.getClass().getInterfaces()[0]);
                                o = constructor.newInstance(injetObject);
                            }

                        }

                        //判断是不是byType
                        if(o == null && flag && loadTypeAttribute.getValue().equalsIgnoreCase("byType")){

                            Field[] beanHasFields = beanClass.getDeclaredFields();
                            for(Field field : beanHasFields){
                                if(field.isAnnotationPresent(Autowired.class)){
                                    Class<?> fildType = field.getType();

                                    int count = 0;
                                    Object injetObject = null;
                                    for(String key : map.keySet()){
                                        Class<?> tmpClazz = map.get(key).getClass().getInterfaces()[0];
                                        if(tmpClazz.getName().equalsIgnoreCase(fildType.getName())){
                                           injetObject = map.get(key);
                                           count ++;
                                       }
                                    }
                                    if(count > 1){
                                        throw new RuntimeException("当前容器中有俩个对象，不知道复制给谁");
                                    }
                                    o = beanClass.newInstance();
                                    field.setAccessible(true);
                                    field.set(o,injetObject);
                                }
                            }
                        }

                        //没有字标签
                        if(o == null){
                            o = beanClass.newInstance();
                        }
                        //存放IOC
                        map.put(beanName,o);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }

                }
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String name){
        return map.get(name);
    }

    public Object getBean(Class clazz){

        for(String key : map.keySet()){
            Object o = map.get(key);
            if(o.getClass().getName().equalsIgnoreCase(clazz.getName())){
                return o;
            }
        }
        return null;
    }
}

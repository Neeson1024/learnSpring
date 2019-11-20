package com.learn.importService;

import com.learn.dao.UserInfoDao;
import com.learn.factoryBean.MyFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserInfoDao.class);
        BeanDefinition beanDefinition  = beanDefinitionBuilder.getBeanDefinition();
        System.out.println(beanDefinition.getBeanClassName());

        //这里不要放在setBeanClass后面，不然会变成MyFactory的class
        //这里的getBeanClassName ===== 》com.learn.dao.UserInfoDao
        //这里就相当于<bean><constructor-arg ref="com.learn.dao.UserInfoDao"/></bean>
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());

        //设置FactoryBean,用FactoryBean创建对象，MyBatis的MapperScan里用的就是这种方式
        ((AbstractBeanDefinition) beanDefinition).setBeanClass(MyFactoryBean.class);
        registry.registerBeanDefinition("userInfoDao",beanDefinition);
    }
}

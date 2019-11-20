package com.learn.importSelects;

import com.learn.Anno.MyAopCircuit;
import com.learn.AopService.MyAopBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyAopBeanPostProcessor.class);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionBuilder.getBeanDefinition();
        Class<?> introspectedClass = ((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass();
        MyAopCircuit declaredAnnotation = introspectedClass.getDeclaredAnnotation(MyAopCircuit.class);
        String value = declaredAnnotation.value();

        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(value);
        registry.registerBeanDefinition("myAopBeanPostProcessor",beanDefinition);
    }
}

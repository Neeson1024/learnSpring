package com.learn.Anno;

import com.learn.importSelects.MyImportBeanDefinitionRegistrar;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportBeanDefinitionRegistrar.class)
public @interface MyAopCircuit {
    String value()default "";
}

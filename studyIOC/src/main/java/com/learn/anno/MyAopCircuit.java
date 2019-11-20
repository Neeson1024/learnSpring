package com.learn.anno;

import com.learn.importService.MyNewImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自己实现的AOP开关
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(MyNewImportBeanDefinitionRegistrar.class)
public @interface MyAopCircuit {

    String value() default "";
}

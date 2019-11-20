package com.learn.Config;

import com.learn.anno.MyAopCircuit;
import com.learn.importService.MyImportBeanDefinitionRegistrar;
import com.learn.importService.MyImportSelectService;
import com.learn.importService.MyNewImportBeanDefinitionRegistrar;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.learn.Config")
@MyAopCircuit("com.learn.MyAspect")
public class MyConfig {
}

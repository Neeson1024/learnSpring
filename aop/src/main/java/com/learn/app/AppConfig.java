package com.learn.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.learn.*")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
}

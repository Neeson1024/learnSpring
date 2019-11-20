package com.learn.config;

import com.learn.Anno.MyAopCircuit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.learn.dao")
@MyAopCircuit("com.learn.config.aopConfig")
public class MyConfig {
}

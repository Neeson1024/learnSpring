package com.learn.importService;

import com.learn.service.UserService;
import com.learn.service.UserServiceImpl;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelectService implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{UserServiceImpl.class.getName()};
    }
}

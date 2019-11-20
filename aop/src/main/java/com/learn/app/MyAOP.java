package com.learn.app;

import com.learn.service.Dao;
import com.learn.service.DaoImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Aspect("perthis(this(com.learn.service.InfoServiceImpl))")
public class MyAOP {


    /**
     * value：表示对那个包，那个类进行代理
     * defaultImpl：默认实现类
     */
    @DeclareParents(value = "com.learn.service.InfoServiceImpl",defaultImpl = DaoImpl.class)
    public static Dao dao;

    @Pointcut("@annotation(com.learn.anno.MyAnnoTwo)")
    public void test(){

    }


    @Pointcut("execution(* com.learn.service.*.*(..))")
    public void pointcut(){

    }

    //@Before("pointcut()")
    //public void Befor(){
    //    System.out.println("before");
    //}

    //@Around("within(com.learn.service.InfoServiceImpl)")
    //public void pointcutAround(ProceedingJoinPoint joinPoint){
    //
    //    System.out.println("Around-Before");
    //    //获取这个连接点的所有方法
    //    Object[] args = joinPoint.getArgs();
    //
    //    for(int i = 0;i<args.length;i++){
    //        args[i] = args[i] + "word";
    //    }
    //
    //    try {
    //        //执行连接点的方法
    //        //joinPoint.proceed();
    //        //执行有参数的方法
    //        joinPoint.proceed(args);
    //    } catch (Throwable throwable) {
    //        throwable.printStackTrace();
    //    }
    //
    //    System.out.println("Around-After");
    //}
}

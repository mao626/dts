package com.mao.aop;

import com.mao.annotation.DisTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by mao on 2018/5/1.
 */
@Component
@Aspect
public class DisTransactionAspect {

    /**
     * 切入点是使用了DisTransaction注解的方法
     */
    @Pointcut("@annotation(com.mao.annotation.DisTransaction)")
    public void aspect() {

    }

    /**
     * 定义环绕通知，此处需要执行prepare方法
     */
    @Around(value = "aspect() && @annotation(disTransaction)")
    public void around(ProceedingJoinPoint pjp, DisTransaction disTransaction) {

        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        pjp.getSignature().getDeclaringType();
    }

}

package com.mao.aop;

import com.mao.annotation.DisTransaction;
import com.mao.bean.InvocationMethodInfo;
import com.mao.service.ExecuteMethodUtil;
import com.mao.service.InvokeService;
import com.mao.service.ThreadLocalHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
    public void around(ProceedingJoinPoint pjp, DisTransaction disTransaction) throws Throwable {
        Method method = ((MethodSignature) (pjp.getSignature())).getMethod();
        Object[] args = pjp.getArgs();
        InvocationMethodInfo prepare = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.prepare(),
                args, method.getParameterTypes());
        InvocationMethodInfo rollback = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.rollback(),
                args, method.getParameterTypes());
        InvocationMethodInfo commit = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.commit(),
                args, method.getParameterTypes());
        String txid = ThreadLocalHolder.getCurrent().getTxid();
        ExecuteMethodUtil util = new ExecuteMethodUtil(txid, rollback, commit);
        ThreadLocalHolder.getCurrent().add(util);
        InvokeService.invoke(prepare);
        pjp.proceed(pjp.getArgs());
    }

}

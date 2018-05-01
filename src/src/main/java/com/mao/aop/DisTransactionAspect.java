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
    public void around(ProceedingJoinPoint pjp, DisTransaction disTransaction) throws Throwable {
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        InvocationMethodInfo prepare = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.prepare(),
                args, argTypes);
        InvocationMethodInfo rollback = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.rollback(),
                args, argTypes);
        InvocationMethodInfo commit = new InvocationMethodInfo(
                pjp.getTarget().getClass(), disTransaction.commit(),
                args, argTypes);
        String txid = ThreadLocalHolder.getCurrent().getTxid();
        ExecuteMethodUtil util = new ExecuteMethodUtil(txid, rollback, commit);
        ThreadLocalHolder.getCurrent().add(util);
        InvokeService.invoke(prepare);
        pjp.proceed(pjp.getArgs());
    }

}

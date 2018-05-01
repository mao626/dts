package com.mao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mao on 2018/5/1.
 * 分布式事务方法注解，使用该注解作用的方法主要定义prepare、rollback、commit方法
 * 注意：一阶段和二阶段的方法需要和注解方法有相同的方法签名
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DisTransaction {
    /**
     * 一阶段预占资源方法
     * @return
     */
    String prepare();

    /**
     * 二阶段回滚方法
     * @return
     */
    String rollback();

    /**
     * 二阶段提交方法
     * @return
     */
    String commit();
}

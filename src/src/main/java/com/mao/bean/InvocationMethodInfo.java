/**
 * @(#)InvocationMethod, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.bean;

/**
 * @author 王东
 * @date 2018/5/1
 */
public class InvocationMethodInfo {
    private Class targetClass;
    private String methodName;
    private Object[] args;
    private Class[] argTypes;

    public InvocationMethodInfo(
            Class targetClass, String methodName,
            Object[] args, Class[] argTypes) {
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.args = args;
        this.argTypes = argTypes;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }
}

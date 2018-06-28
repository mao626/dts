/**
 * @(#)InvockService, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.service;

import com.mao.bean.InvocationMethodInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 王东
 * @date 2018/5/1
 */
public class InvokeService {

    public static void invoke(InvocationMethodInfo invocationMethodInfo)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class targetClass = invocationMethodInfo.getTargetClass();
        Object instance = targetClass.newInstance();
        Method method = targetClass.getMethod(
                invocationMethodInfo.getMethodName(), invocationMethodInfo.getArgTypes());
        method.invoke(instance, invocationMethodInfo.getArgs());
    }

}

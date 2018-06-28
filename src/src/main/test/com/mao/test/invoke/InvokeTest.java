package com.mao.test.invoke; /**
 * @(#)InvockTest, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.alibaba.fastjson.JSON;
import com.mao.bean.InvocationMethodInfo;
import com.mao.service.InvokeService;
import com.mao.test.BaseTest;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王东
 * @date 2018/5/1
 */
public class InvokeTest extends BaseTest {

    @Test
    public void testInvoke() {
        Class targetClass = RunTest.class;
        String name = "run2";
        Object[] args = new Object[3];
        args[0] = 1;
        args[1] = "string";
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        args[2] = list;
        Class[] argTypes = {int.class, String.class, List.class};
        InvocationMethodInfo info = new InvocationMethodInfo(targetClass, name, args, argTypes);
        InvokeService invokeService = new InvokeService();
        try {
            invokeService.invoke(info);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testType() {
        Class targetClass = RunTest.class;
        Class[] classes = new Class[]{int.class, java.lang.String.class, java.util.List.class};
        try {
            Method method = targetClass.getMethod("run1", classes);
            for (Class<?> c : method.getParameterTypes()) {
                System.out.println(c.getClass());
            }
            System.out.println(JSON.toJSONString(method.getParameterTypes()));
            System.out.println(method.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

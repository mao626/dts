package com.mao.test.invoke; /**
 * @(#)RunTest, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 王东
 * @date 2018/5/1
 */
public class RunTest {

    public void run1(int i, String s, List<String> list) {
        System.out.println("run1 " + "i:" + i + ",s:"+ s +",list:"+ JSON.toJSONString(list));
    }

    public void run2(int i, String s, List<String> list) {
        System.out.println("run2 " + "i:" + i + ",s:"+ s +",list:"+ JSON.toJSONString(list));
    }
}

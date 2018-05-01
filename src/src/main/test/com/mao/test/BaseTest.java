package com.mao.test; /**
 * @(#)BaseTest, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 王东(hzwangdong @ corp.netease.com)
 * @date 2018/5/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-test.xml")
public class BaseTest {

    @Test
    public void init() {
        System.out.println("init");
    }
}

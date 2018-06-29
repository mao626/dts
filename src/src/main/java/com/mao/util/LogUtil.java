/**
 * @(#)LogUtil, 2018年06月29日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 王东
 * @date 2018/6/29
 */
public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger("DTS");

    public static Logger getLogger() {
        return logger;
    }
}

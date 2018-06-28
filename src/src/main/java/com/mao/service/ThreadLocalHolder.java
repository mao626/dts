/**
 * @(#)MyThreadLocal, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.service;

import com.mao.bean.DisTransactionInfo;

/**
 * @author 王东
 * @date 2018/5/1
 */
public class ThreadLocalHolder {

    private static ThreadLocal<DisTransactionInfo> threadLocal
            = new ThreadLocal<DisTransactionInfo>();


    public static DisTransactionInfo getCurrent() {
        return threadLocal.get();
    }

    public static void setThreadLocal(DisTransactionInfo info) {
        threadLocal.set(info);
    }

    public static void clear() {
        threadLocal.remove();
    }
}

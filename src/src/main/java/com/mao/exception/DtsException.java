/**
 * @(#)DtsException, 2018年06月13日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.exception;

/**
 * @author 王东
 * @date 2018/6/13
 */
public class DtsException extends RuntimeException {

    public DtsException() {

    }

    public DtsException(String message) {
        super(message);
    }

    public DtsException(String message, Throwable ex) {
        super(message, ex);
    }
}

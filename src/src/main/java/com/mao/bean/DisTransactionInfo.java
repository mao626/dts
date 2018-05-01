/**
 * @(#)DisTransactionInfo, 2018年05月01日.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mao.bean;

import com.mao.service.ExecuteMethodUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王东(hzwangdong @ corp.netease.com)
 * @date 2018/5/1
 */
public class DisTransactionInfo {
    private String txid;

    private List<ExecuteMethodUtil> methodUtilList = new ArrayList<ExecuteMethodUtil>();


    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public void setMethodUtilList(List<ExecuteMethodUtil> methodUtilList) {
        this.methodUtilList = methodUtilList;
    }

    public void add(ExecuteMethodUtil method) {
        methodUtilList.add(method);
    }

    public List<ExecuteMethodUtil> getMethodUtilList() {
        return methodUtilList;
    }
}

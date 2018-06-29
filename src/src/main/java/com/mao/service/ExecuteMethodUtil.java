package com.mao.service;

import com.alibaba.fastjson.JSON;
import com.mao.bean.InvocationMethodInfo;
import com.mao.util.LogUtil;
import org.slf4j.Logger;

/**
 * Created by mao on 2018/5/1.
 */
public class ExecuteMethodUtil {

    private static final Logger logger = LogUtil.getLogger();

    private String txid;

    private InvocationMethodInfo rollback;

    private InvocationMethodInfo commit;


    public ExecuteMethodUtil(
            String txid, InvocationMethodInfo rollback,
            InvocationMethodInfo commit) {
        this.txid = txid;
        this.rollback = rollback;
        this.commit = commit;
    }

    public boolean commit() {
        try {
            InvokeService.invoke(commit);
        } catch (Exception e) {
            logger.error("execute commit error. commit:{}", JSON.toJSONString(commit));
            return false;
        }
        return true;
    }

    public boolean rollback() {
        try {
            InvokeService.invoke(rollback);
        } catch (Exception e) {
            logger.error("execute rollback error. rollback:{}", JSON.toJSONString(rollback));
            return false;
        }
        return true;
    }

}

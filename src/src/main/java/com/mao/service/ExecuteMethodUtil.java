package com.mao.service;

import com.mao.bean.InvocationMethodInfo;
import org.springframework.stereotype.Service;

/**
 * Created by mao on 2018/5/1.
 */
@Service
public class ExecuteMethodUtil {

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
            // todo 日志
            return false;
        }
        return true;
    }

    public boolean rollback() {
        try {
            InvokeService.invoke(rollback);
        } catch (Exception e) {
            // todo 日志
            return false;
        }
        return true;
    }

}

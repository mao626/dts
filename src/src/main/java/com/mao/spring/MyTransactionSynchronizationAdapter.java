package com.mao.spring;

import com.mao.service.TransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * Created by mao on 2018/5/1.
 */
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {

    private TransactionManager manager;

    public MyTransactionSynchronizationAdapter(TransactionManager manager) {
        this.manager = manager;
    }

    @Override
    public void afterCompletion(int status) {
        super.afterCompletion(status);
        switch (status) {
            case TransactionSynchronization.STATUS_UNKNOWN:
                //todo 日志打印
                break;
            case TransactionSynchronization.STATUS_COMMITTED:
                manager.commit();
                break;
            case TransactionSynchronization.STATUS_ROLLED_BACK:
                manager.rollback();
                break;
            default:
                break;
        }
    }
}

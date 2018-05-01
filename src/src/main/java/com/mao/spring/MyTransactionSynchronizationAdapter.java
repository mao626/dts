package com.mao.spring;

import com.mao.service.ExecuteService;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * Created by mao on 2018/5/1.
 */
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {

    private ExecuteService service;

    public MyTransactionSynchronizationAdapter(ExecuteService service) {
        this.service = service;
    }

    @Override
    public void afterCompletion(int status) {
        super.afterCompletion(status);
        switch (status) {
            case TransactionSynchronization.STATUS_UNKNOWN:
                //todo 日志打印
                break;
            case TransactionSynchronization.STATUS_COMMITTED:
                service.commit();
                break;
            case TransactionSynchronization.STATUS_ROLLED_BACK:
                service.rollback();
                break;
        }
    }
}

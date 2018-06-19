package com.mao.spring;

import com.mao.service.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * Created by mao on 2018/5/1.
 */
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MyTransactionSynchronizationAdapter.class);

    private TransactionManager manager;

    public MyTransactionSynchronizationAdapter(TransactionManager manager) {
        this.manager = manager;
    }

    @Override
    public void afterCompletion(int status) {
        super.afterCompletion(status);
        switch (status) {
            case TransactionSynchronization.STATUS_UNKNOWN:
                logger.error("unknow status error.");
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

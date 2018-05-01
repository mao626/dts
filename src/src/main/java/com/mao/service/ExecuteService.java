package com.mao.service;

import com.mao.spring.MyTransactionSynchronizationAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Created by mao on 2018/5/1.
 */
@Service
public class ExecuteService {

    public void commit() {

    }

    public void rollback() {

    }


    public void registerSynchronization() {
        MyTransactionSynchronizationAdapter adapter =
                new MyTransactionSynchronizationAdapter(this);
        TransactionSynchronizationManager.registerSynchronization(adapter);
    }
}

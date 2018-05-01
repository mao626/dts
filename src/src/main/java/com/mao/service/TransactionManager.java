package com.mao.service;

import com.mao.bean.DisTransactionInfo;
import com.mao.spring.MyTransactionSynchronizationAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

/**
 * Created by mao on 2018/5/1.
 */
@Component
public class TransactionManager {

    /**
     * 开启分布式事务
     * 这里需要做：
     * 1.新建事务插入到db，状态为init
     *
     * 2.把db中的事务状态更新为commit
     *
     * 步骤1需要在业务事务外，步骤2需要在业务事务里
     */
    public void start() {
        if (ThreadLocalHolder.getCurrent() != null) {
            DisTransactionInfo info = new DisTransactionInfo();
            info.setTxid(UUID.randomUUID().toString());
            ThreadLocalHolder.setThreadLocal(info);
        } else {
            return;
        }

        insertInitTransaction();

        updateTransaction();

        registerSynchronization();
    }

    /**
     * 新建分布式事务并插入数据库，不能和主事务处于一个事务中
     */
    private void insertInitTransaction() {
        // todo
    }

    /**
     * 把init状态的分布式事务更新为commit状态，需要在主事务内
     */
    private void updateTransaction() {

    }

    public void rollback() {
        boolean success = true;
        DisTransactionInfo current = ThreadLocalHolder.getCurrent();
        for (ExecuteMethodUtil util: current.getMethodUtilList()) {
            success = success & util.rollback();
        }
        String txid = current.getTxid();
        if (success) {
            deleteTransaction(txid);
        } else {
            alarm(txid);
        }
        ThreadLocalHolder.clear();
    }

    public void commit() {
        boolean success = true;
        DisTransactionInfo current = ThreadLocalHolder.getCurrent();
        for (ExecuteMethodUtil util: current.getMethodUtilList()) {
            success = success & util.commit();
        }
        String txid = current.getTxid();
        if (success) {
            deleteTransaction(txid);
        } else {
            alarm(txid);
        }
        ThreadLocalHolder.clear();
    }

    private void deleteTransaction(String txid) {

    }

    private void alarm(String txid) {

    }

    public void registerSynchronization() {
        MyTransactionSynchronizationAdapter adapter =
                new MyTransactionSynchronizationAdapter(this);
        TransactionSynchronizationManager.registerSynchronization(adapter);
    }
}

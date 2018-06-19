package com.mao.service;

import com.alibaba.fastjson.JSON;
import com.mao.bean.DisTransactionInfo;
import com.mao.exception.DtsException;
import com.mao.spring.MyTransactionSynchronizationAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by mao on 2018/5/1.
 */
public class TransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

    private DataSource dataSource;
    /**
     * 开启分布式事务
     * 这里需要做：
     * 1.新建事务插入到db，状态为init
     *
     * 2.把db中的事务状态更新为commit
     *
     * 步骤1需要在业务事务外，步骤2需要在业务事务里
     */
    public void start(String businessType, String businessId) {
        if (ThreadLocalHolder.getCurrent() == null) {
            DisTransactionInfo info = new DisTransactionInfo();
            info.setTxid(UUID.randomUUID().toString());
            ThreadLocalHolder.setThreadLocal(info);
        } else {
            return;
        }

        insertInitTransaction(businessType, businessId);

        updateTransaction();

        registerSynchronization();
    }

    /**
     * 新建分布式事务并插入数据库，不能和主事务处于一个事务中
     */
    private void insertInitTransaction(String businessType, String businessId) {
        Connection connection = null;
        String txid = ThreadLocalHolder.getCurrent().getTxid();
        try {
            long curTime = System.currentTimeMillis();
            connection = dataSource.getConnection();
            String insertSql = "insert into DTS_TRANSACTION(txid,businessType,businessId,status,createTime,updateTime)" +
                    " values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setString(1, txid);
            statement.setString(2, businessType);
            statement.setString(3, businessId);
            statement.setInt(4, 0);
            statement.setLong(5, curTime);
            statement.setLong(6, curTime);
            statement.execute();
        } catch (SQLException e) {
            throw new DtsException("insert init transaction error, business type="
                    + businessType + ", business id=" + businessId + ", txid=" + txid, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("insert init transaction error. business type:{}, business id:{}, txid:{}",
                            businessType, businessId, txid, e);
                }
            }
        }
    }

    /**
     * 把init状态的分布式事务更新为commit状态，需要在主事务内
     */
    private void updateTransaction() {
        String txid = ThreadLocalHolder.getCurrent().getTxid();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            long curTime = System.currentTimeMillis();
            String updateSql = "update DTS_TRANSACTION set status=?, updateTime=? where txid=?";
            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setInt(1, 1);
            statement.setLong(2, curTime);
            statement.setString(3, txid);
            statement.execute();
        } catch (SQLException e) {
            logger.error("update transaction error. txid:{}", txid, e);
            throw new DtsException("update transaction error. txid=" + txid, e);
        }
    }

    private void deleteTransaction(String txid) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String deleteSql = "delete from DTS_TRANSACTION where txid=?";
            PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setString(1, txid);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("delete transaction error. txid:{}", txid, e);
                }
            }
        }
    }

    public void rollback() {
        boolean success = true;
        DisTransactionInfo current = ThreadLocalHolder.getCurrent();
        for (ExecuteMethodUtil util: current.getMethodUtilList()) {
            if (!util.rollback()) {
                success = false;
                alarmAndAdd(util);
            }
        }
        String txid = current.getTxid();
        if (success) {
            deleteTransaction(txid);
        }
        ThreadLocalHolder.clear();
    }

    public void commit() {
        boolean success = true;
        DisTransactionInfo current = ThreadLocalHolder.getCurrent();
        for (ExecuteMethodUtil util: current.getMethodUtilList()) {
            success = success & util.commit();
            if (!util.commit()) {
                success = false;
                alarmAndAdd(util);
            }
        }
        String txid = current.getTxid();
        if (success) {
            deleteTransaction(txid);
        }
        ThreadLocalHolder.clear();
    }

    /**
     * 存储分布式事务参与者信息
     */
    private void alarmAndAdd(ExecuteMethodUtil util) {
        String txid = ThreadLocalHolder.getCurrent().getTxid();
        long curTime = System.currentTimeMillis();
        String insertSql = "insert into DTS_PARTICIPANT(txid, name, status, context, createTime, updateTime)" +
                " values (?,?,?,?,?,?);";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setString(1, txid);
            statement.setString(2, "");
            statement.setInt(3, 0);
            statement.setString(4, JSON.toJSONString(util));
            statement.setLong(5, curTime);
            statement.setLong(6, curTime);
            statement.execute();
        } catch (SQLException e) {
            throw new DtsException("add transaction partition error.", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("add transaction partition error.  txid:{}", txid, e);
                }
            }
        }
    }

    private void registerSynchronization() {
        MyTransactionSynchronizationAdapter adapter =
                new MyTransactionSynchronizationAdapter(this);
        TransactionSynchronizationManager.registerSynchronization(adapter);
    }
}

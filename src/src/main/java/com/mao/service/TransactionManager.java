package com.mao.service;

import org.springframework.stereotype.Component;

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
        //todo
    }
}

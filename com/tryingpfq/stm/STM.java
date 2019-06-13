package com.tryingpfq.stm;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:51
 */
public class STM {

    //私有构造方法
    private STM(){

    }

    public static final Object commitLock = new Object();

    /**
     * 原子化提交
     */
    public static void atomicComit(TxnRunnable action) {
        boolean committed = false;
        while (!committed) {
            STMTxn txn = new STMTxn();
            action.run(txn);
            committed = txn.commit();
        }
    }
}

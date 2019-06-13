package com.tryingpfq.stm;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:53
 */
@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}

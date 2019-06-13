package com.tryingpfq.stm;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:38
 *
 * 事物通用接口
 */
public interface Txn {
    <T> T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref,T value);
}

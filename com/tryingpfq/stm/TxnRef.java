package com.tryingpfq.stm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:36
 *
 *  支持事物的引用
 */
public class TxnRef<T> {
    public volatile VersionRef curRef;  //当前版本号

    public TxnRef(T value) {
        this.curRef = new VersionRef(value,0L);
    }

    public TxnRef() {

    }

    public VersionRef getCurRef(){
        return curRef;
    }

    //获取当前事物中的数据
    public T getValue(Txn txn) {
        return txn.get(this);
    }

    //在当前事物中设置数据
    public void setValue(T value, Txn txn) {
        txn.set(this,value);
    }
}



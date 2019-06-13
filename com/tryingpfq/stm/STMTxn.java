package com.tryingpfq.stm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:40
 * 事物实现类
 */
public class STMTxn implements Txn {
    /**
     * 事物ID生成器
     */
    private static AtomicLong txnSeq = new AtomicLong(0L);

    /**
     * 当前事物所有相关数据
     */
    private Map<TxnRef,VersionRef> inTxnMap = new HashMap<>();

    /**
     * 当前事物所有需要修改的数据
     */
    private Map<TxnRef,Object> writeMap = new HashMap<>();

    /** 当前事物ID **/
    private final long txnId;

    public STMTxn(){
        txnId = txnSeq.incrementAndGet();
    }

    @Override
    public <T> T get(TxnRef<T> ref) {
        if (inTxnMap.get(ref) == null) {
            inTxnMap.put(ref, ref.getCurRef());
        }
        return (T) inTxnMap.get(ref).getValue();
    }

    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        if (inTxnMap.get(ref) == null) {
            inTxnMap.put(ref, ref.getCurRef());
        }
        writeMap.put(ref, value);
    }

    /**
     * 提交事物
     */
    public boolean commit(){
        boolean isValid = true;
        synchronized (STM.commitLock) {
            for (Map.Entry<TxnRef, VersionRef> entry : inTxnMap.entrySet()) {
                VersionRef curRef = entry.getKey().getCurRef();
                VersionRef readRef = entry.getValue();
                // 通过版本号验证数据是否发生变化
                if (curRef.getVersion() != readRef.getVersion()) {
                    isValid = false;
                    break;
                }
            }
            //如果校验通过，则所有修改生效
            if (isValid) {
                writeMap.forEach((k,v) -> {
                    k.curRef = new VersionRef(v,txnId);
                });
            }
        }
        return isValid;
    }
}

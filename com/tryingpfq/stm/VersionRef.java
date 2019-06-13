package com.tryingpfq.stm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tryingpfq
 * @date 2019/6/13 14:31
 * 带版本号的对象引用
 */
public class VersionRef<T> {
    private T value;

    private long version;


    public VersionRef(T value,long version) {
        this.value = value;
        this.version = version;
    }

    public T getValue(){
        return value;
    }

    public long getVersion(){
        return version;
    }
}

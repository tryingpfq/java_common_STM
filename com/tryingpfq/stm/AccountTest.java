package com.tryingpfq.stm;

/**
 * @author tryingpfq
 * @date 2019/6/13 15:11
 *
 * 转账测试
 *
 */
public class AccountTest {
    //余额
    private TxnRef<Integer> balance;

    public AccountTest(int balance){
        this.balance = new TxnRef<Integer>(balance);
    }

    //转账
    public void transfer(AccountTest target,int amt) {
        STM.atomicComit(txn -> {
            Integer from = balance.getValue(txn);
            balance.setValue(from - amt,txn);
            Integer to = (Integer) target.getTxnRef().getValue(txn);
            target.getTxnRef().setValue(to + amt,txn);
        });
    }

    public TxnRef getTxnRef(){
        return balance;
    }

}

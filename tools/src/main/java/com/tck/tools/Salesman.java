package com.tck.tools;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/22</p>
 *
 * @author tck
 * @version 1.0
 */
public class Salesman implements IBank {

    private IBank iBank;

    public Salesman(IBank iBank) {
        this.iBank = iBank;
    }

    @Override
    public void applyBank() {
        System.out.println("办理开始");
        //调用我们的方法
        iBank.applyBank();
        System.out.println("办理完毕");
    }
}

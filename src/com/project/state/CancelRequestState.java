package com.project.state;

import com.project.Product;
import com.project.VendingMachine;

public class CancelRequestState implements State {
    VendingMachine vendingMachine;

    public CancelRequestState(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void collectMoney(int money) {
        throw new RuntimeException("Unable to insert money when choosing cancel request");
    }

    @Override
    public void selectProduct(Product product) {
        throw new RuntimeException("Unable to select product when choosing cancel request");
    }

    @Override
    public void dispense() {
        throw new RuntimeException("Unable to dispense product when choosing cancel request");
    }

    @Override
    public void cancelRequest() {
        System.out.println("Your " + vendingMachine.refundMoney() + " is refunded");
        vendingMachine.changeState(new AvailableState(vendingMachine));
    }
}

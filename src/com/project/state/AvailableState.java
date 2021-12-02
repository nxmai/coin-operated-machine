package com.project.state;

import com.project.BankNote;
import com.project.Product;
import com.project.VendingMachine;

public class AvailableState implements State {
    VendingMachine vendingMachine;

    public AvailableState(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void collectMoney(int money) {
        boolean isSuitableMoney = false;
        for(BankNote note: BankNote.values()){
            if(note.getValue() == money){
                isSuitableMoney = true;
                break;
            }
        }
        vendingMachine.setCollectedMoney(money);
        if(!isSuitableMoney) {
            System.out.println("Money is not suitable");
            vendingMachine.changeState(new CancelRequestState(vendingMachine));
            vendingMachine.cancelRequest();
        }
    }

    @Override
    public void selectProduct(Product product) {
        vendingMachine.changeState(new InsertProductState(vendingMachine));
    }

    @Override
    public void dispense() {
        if(vendingMachine.getCollectedMoney() != 0){
            vendingMachine.changeState(new InsertProductState(vendingMachine));
            vendingMachine.dispense();
        } else {
            throw new RuntimeException("No money inserted");
        }
    }

    @Override
    public void cancelRequest() {
        if(vendingMachine.getCollectedMoney() != 0){
            vendingMachine.changeState(new CancelRequestState(vendingMachine));
            vendingMachine.cancelRequest();
        } else {
            throw new RuntimeException("No money inserted");
        }
    }
}

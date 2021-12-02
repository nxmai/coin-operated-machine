package com.project.state;

import com.project.Product;
import com.project.VendingMachine;

public class InsertProductState implements State {
    VendingMachine vendingMachine;

    public InsertProductState(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void collectMoney(int money) {
        throw new RuntimeException("Money inserted");
    }

    @Override
    public void selectProduct(Product product) {

    }

    @Override
    public void dispense() {
        if(vendingMachine.getSelectedProduct() != null){
            vendingMachine.changeState(new DispenseState(vendingMachine));
            vendingMachine.dispense();
        } else {
            throw new RuntimeException("No product selected");
        }
    }

    @Override
    public void cancelRequest() {
        vendingMachine.changeState(new CancelRequestState(vendingMachine));
        vendingMachine.cancelRequest();
    }
}

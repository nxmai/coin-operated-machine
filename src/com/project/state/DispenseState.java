package com.project.state;

import com.project.Product;
import com.project.VendingMachine;

public class DispenseState implements State {
    VendingMachine vendingMachine;

    public DispenseState(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void collectMoney(int money) {
        throw new RuntimeException("Unable to insert money when dispensing product and change");
    }

    @Override
    public void selectProduct(Product product) {
        throw new RuntimeException("Unable to collect product when dispensing product and change");
    }

    @Override
    public void dispense() {
        int moneyChange = vendingMachine.calculateChange();
        if(moneyChange >= 0){
            System.out.println("Your " + vendingMachine.getSelectedProduct() + " here.");
            vendingMachine.resetCollectedMoney();
            vendingMachine.changeState(new AvailableState(vendingMachine));

            if(moneyChange > 0){
                System.out.println("Your change is " + moneyChange + " VND.");
            }

            if(vendingMachine.getIsReceivePromotion()){
                System.out.println("You are the lucky guy to receive a COKE for free");
                System.out.println("Your lucky " + vendingMachine.getPromotionProduct() + " here");
                vendingMachine.setIsReceivePromotion(false);
            }

        }
        else {
            System.out.println("Money inserted is not enough");
            vendingMachine.changeState(new CancelRequestState(vendingMachine));
            vendingMachine.cancelRequest();
        }
        vendingMachine.setSelectedProduct(null);
    }

    @Override
    public void cancelRequest() {
        throw new RuntimeException("Unable to cancel request when dispensing product and change");
    }
}

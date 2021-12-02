package com.project;

import com.project.state.AvailableState;
import com.project.state.State;

public class VendingMachine {
    private State state;
    private int collectedMoney = 0;
    private Product selectedProduct;
    final int[] consecutiveProductArray = new int[Product.values().length];
    private int promotionPercent = 10;
    private int promotionBudget = 0;
    final int consecutivePurchases = 3;
    private boolean isReceivePromotion = false;

    private void initConsecutiveProductArray(){
        for(Product product: Product.values()){
            consecutiveProductArray[product.getNumber()] = 0;
        }
    }

    public VendingMachine(){
        this.state = new AvailableState(this);
    }

    public void changeState(State state){
        this.state = state;
    }

    public void setCollectedMoney(int money){
        this.collectedMoney += money;
    }

    public int getCollectedMoney(){
        return collectedMoney;
    }

    public void setSelectedProduct(Product selectedProduct){
        this.selectedProduct = selectedProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public boolean getIsReceivePromotion(){
        return isReceivePromotion;
    }

    public void setIsReceivePromotion(boolean isReceive) {
        this.isReceivePromotion = isReceive;
    }

    public int getPromotionBudget(){
        return promotionBudget;
    }

    public int getPromotionPercent() {
        return promotionPercent;
    }

    public int refundMoney(){
        int refundMoney = collectedMoney;
        resetCollectedMoney();
        return refundMoney;
    }

    public int calculateChange(){
        return collectedMoney - selectedProduct.getPrice();
    }

    public void insertMoney(int money){
        this.state.collectMoney(money);
    }

    private void increasePromotionBudget(Product promoProduct) {
        this.promotionBudget += promoProduct.getPrice();
    }

    private void resetPromotionBudget() {
        this.promotionBudget = 0;
    }

    public void resetCollectedMoney () {
        this.collectedMoney = 0;
    }

    public Product getPromotionProduct(){
        return Product.COKE;
    }

    public void insertProduct(Product product){
        this.state.selectProduct(product);
        if(this.selectedProduct != null){           //check the case user insert product many time
            if(this.selectedProduct != product){    //set selected product if the current insert different from the previous
                setSelectedProduct(product);
            }
            return;
        }

        setSelectedProduct(product);

        int productIndex = product.getNumber();

        if(consecutiveProductArray[productIndex] != 0){
            consecutiveProductArray[productIndex] += 1;
        } else {
            initConsecutiveProductArray();
            consecutiveProductArray[productIndex] = 1;
        }

        if(consecutiveProductArray[productIndex] == consecutivePurchases){
            int randomNumber = (int)(Math.random() * 100);
            if(randomNumber < promotionPercent){
                this.isReceivePromotion = true;
                increasePromotionBudget(getPromotionProduct());
            }
            initConsecutiveProductArray();
        }
    }

    public void dispense(){
        this.state.dispense();
    }

    public void cancelRequest(){
        this.state.cancelRequest();
    }

    public void calculatePromotionInEndDay() {
        if(promotionBudget < 50000){
            this.promotionPercent = 50;
        } else {
            this.promotionPercent = 10;
        }
        resetPromotionBudget();
    }
}

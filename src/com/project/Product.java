package com.project;

public enum Product {
    COKE(0, 10000),
    PEPSI(1, 10000),
    SODA(2, 20000);

    private int number;
    private int price;

    Product(int number, int price){
        this.number = number;
        this.price = price;
    }

//    public final int getNumberOfProduct{
//        return Product.values().length;
//    }

    public int getPrice(){
        return this.price;
    }
//
    public int getNumber() { return this.number; }
}

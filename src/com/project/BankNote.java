package com.project;

public enum BankNote {
    TEN(10000),
    TWENTY(20000),
    FIFTY(50000),
    ONE_HUNDRED(100000),
    TWO_HUNDRED(200000);

    private int value;

    BankNote(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}

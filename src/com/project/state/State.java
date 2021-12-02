package com.project.state;

import com.project.Product;

public interface State {
    public void collectMoney(int money);
    public void selectProduct(Product product);
    public void dispense();
    public void cancelRequest();
}

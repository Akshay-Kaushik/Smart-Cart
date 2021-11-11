package com.example.smartcart;

public class ModelClass {
    private String product, quantity, price ;

    ModelClass(String product, String quantity, String price){
        this.product=product;
        this.quantity=quantity;
        this.price=price;
    }

    public String getProduct() {
        return product;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}

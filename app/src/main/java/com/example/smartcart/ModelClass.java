package com.example.smartcart;

public class ModelClass {
    private int imageView;
    private String product, quantity, price ;

    ModelClass(int imageView,String product, String quantity, String price){
        this.imageView=imageView;
        this.product=product;
        this.quantity=quantity;
        this.price=price;
    }
    public int getImageView() {
        return imageView;
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

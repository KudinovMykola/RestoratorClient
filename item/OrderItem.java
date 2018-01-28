package com.kudinov.restoratorclient.item;


import com.kudinov.restoratorclient.model.Product;

public class OrderItem {
    private Integer position;
    private Product product;
    private Integer count;

    public OrderItem(Integer position, Product product, Integer count) {
        this.position = position;
        this.product = product;
        this.count = count;
    }


    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Product getProduct() {
        return product;
    }

    //custom getters
    public String getName() {
        return this.product.getName();
    }
    public Float getPrice() {
        return this.product.getPrice();
    }
    public Float getTotal() {
        Float total = (Float)(getPrice() * count);
        return total;
    }
}

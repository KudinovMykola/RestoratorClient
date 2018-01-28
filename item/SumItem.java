package com.kudinov.restoratorclient.item;

public class SumItem {
    private String title;
    private Float sum;

    public SumItem(String title, Float sum) {
        this.title = title;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }
    public Float getSum() {
        return sum;
    }
}

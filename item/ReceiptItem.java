package com.kudinov.restoratorclient.item;

public class ReceiptItem {
    public enum TypeItem {ORDER,SUM};

    private OrderItem order;
    private SumItem sum;
    private TypeItem type;

    public ReceiptItem(OrderItem orderItem) {
        type = TypeItem.ORDER;
        sum = null;
        order = orderItem;
    }
    public ReceiptItem(SumItem sumItem) {
        type = TypeItem.SUM;
        sum = sumItem;
        order = null;
    }

    public OrderItem getOrder() {
        return order;
    }
    public SumItem getSum() {
        return sum;
    }
    public TypeItem getType() {
        return type;
    }
}

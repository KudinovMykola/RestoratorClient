package com.kudinov.restoratorclient.list;

import com.kudinov.restoratorclient.item.OrderItem;
import com.kudinov.restoratorclient.item.ReceiptItem;
import com.kudinov.restoratorclient.item.SumItem;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ReceiptList {
    private Integer countItems;

    private Float orderedSum;
    private Float reserveSum;
    private Float currentSum;
    private Float totalSum;

    private List<ReceiptItem> receiptItems;

    private List<OrderItem> orderedList;
    private List<OrderItem> reserveList;
    private List<OrderItem> currentList;

    private String titleOrdered;
    private String titleReserve;
    private String titleCurrent;

    public ReceiptList(String ordered, String reserve, String current) {
        countItems = 0;

        orderedSum = 0f;
        reserveSum = 0f;
        currentSum = 0f;
        totalSum = 0f;

        receiptItems = new ArrayList<>();

        orderedList = new ArrayList<>();
        reserveList = new ArrayList<>();
        currentList = new ArrayList<>();

        titleOrdered = ordered;
        titleReserve = reserve;
        titleCurrent = current;
    }

    //receipt list work
    private void zeroCount() {
        countItems = 0;

        orderedSum = 0f;
        reserveSum = 0f;
        currentSum = 0f;
        totalSum = 0f;
    }
    private float addOrderListAndGetSum(List<OrderItem> list) {
        float sum = 0f;
        if(list.size() == 0) {
            return sum;
        }


        for(OrderItem order: list){
            countItems++;
            order.setPosition(countItems);
            sum += order.getTotal();
            receiptItems.add(new ReceiptItem(order));
        }
        return sum;
    }
    private void refreshReceiptList() {
        zeroCount();
        receiptItems.clear();

        orderedSum = addOrderListAndGetSum(orderedList);
        if(orderedSum != 0f)
            receiptItems.add(new ReceiptItem(new SumItem(titleOrdered, orderedSum)));

        reserveSum = addOrderListAndGetSum(reserveList);
        if(reserveSum != 0f)
            receiptItems.add(new ReceiptItem(new SumItem(titleReserve, reserveSum)));

        currentSum = addOrderListAndGetSum(currentList);
        if(currentSum != 0f)
            receiptItems.add(new ReceiptItem(new SumItem(titleCurrent, currentSum)));

        totalSum = orderedSum + reserveSum + currentSum;
    }

    //support order list's
    private OrderItem findOrderItemByProduct(List<OrderItem> itemList, Product product) {
        if(itemList.size() == 0)
            return null;

        for(OrderItem item: itemList) {
            if(product.getId().equals(item.getProduct().getId())) {
                return item;
            }
        }
        return null;
    }
    private void addOneItem(List<OrderItem> itemList, OrderItem addedItem) {
        OrderItem item = findOrderItemByProduct(itemList, addedItem.getProduct());
        if(item != null) {
            int goalCount = item.getCount() + addedItem.getCount();
            item.setCount(goalCount);
            return;
        }

        itemList.add(addedItem);
    }
    private void addListItems (List<OrderItem> baseList, List<OrderItem> addedList) {
        if (addedList.size() == 0)
            return;

        if(baseList.size() == 0) {
            baseList.addAll(addedList);
        } else {
            for (OrderItem item: addedList) {
                addOneItem(baseList, item);
            }
        }
    }

    //public
    public void addToCurrentList(Product product, int count) {
        OrderItem addedItem = new OrderItem(countItems, product, count);
        addOneItem(currentList, addedItem);
        refreshReceiptList();
    }
    public void clearCurrentAndReserve() {
        reserveList.clear();
        currentList.clear();

        refreshReceiptList();
    }
    public void currentToReserve() {
        addListItems(reserveList,currentList);
        currentList.clear();

        refreshReceiptList();
    }
    public void allToOrderList() {
        addListItems(orderedList, currentList);
        currentList.clear();

        addListItems(orderedList,reserveList);
        reserveList.clear();

        refreshReceiptList();
    }

    //getters
    public Integer getCountItems() {
        return countItems;
    }

    public Float getOrderedSum() {
        return orderedSum;
    }
    public Float getReserveSum() {
        return reserveSum;
    }
    public Float getCurrentSum() {
        return currentSum;
    }
    public Float getTotalSum() {
        return totalSum;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }
    public List<OrderItem> getOrderedList() {
        return orderedList;
    }
    public List<OrderItem> getReserveList() {
        return reserveList;
    }
    public List<OrderItem> getCurrentList() {
        return currentList;
    }
}

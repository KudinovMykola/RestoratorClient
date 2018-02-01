package com.kudinov.restoratorclient.item;

import com.kudinov.restoratorclient.datawaiter.OrderElement;

public class ReceiptItem {
    public enum TypeReceipt{CURRENT, RESERVE, ORDERED, SUM}

    private String _position;
    private String _title;
    private String _count;
    private String _price;
    private String _total;
    private TypeReceipt _type;

    public ReceiptItem(Integer position, String productName, Float price, Integer count, Float total, TypeReceipt type) {
        _position = position.toString();
        _title = productName;
        _price = price.toString();
        _count = count.toString();
        _total = total.toString();
        _type = type;
    }
    public ReceiptItem(String itemName, Float sum) {
        _position = "";
        _title = itemName;
        _price = "";
        _count = "";
        _total = sum.toString();
        _type = TypeReceipt.SUM;
    }

    public void set_position(String _position) {
        this._position = _position;
    }

    public String get_position() {
        return _position;
    }
    public String get_title() {
        return _title;
    }
    public String get_count() {
        return _count;
    }
    public String get_price() {
        return _price;
    }
    public String get_total() {
        return _total;
    }
    public TypeReceipt get_type() {
        return _type;
    }
}

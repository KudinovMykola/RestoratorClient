package com.kudinov.restoratorclient.item;

import com.kudinov.restoratorclient.datawaiter.OrderElement;

public class ReceiptItem {
    public enum TypeReceipt{CURRENT, RESERVE, ORDERED, SUM}

    private int _id_product;
    private int _position;
    private String _title;
    private int _count;
    private float _price;
    private float _total;
    private TypeReceipt _type;

    public ReceiptItem(int position, int id, String productName, float price, int count, float total, TypeReceipt type) {
        _position = position;
        _id_product = id;
        _title = productName;
        _price = price;
        _count = count;
        _total = total;
        _type = type;
    }
    public ReceiptItem(String itemName, Float sum) {
        _position = 0;
        _title = itemName;
        _price = 0f;
        _count = 0;
        _total = sum;
        _type = TypeReceipt.SUM;
    }

    public void set_position(int position) {
        this._position = _position;
    }

    public int get_id_product() {
        return _id_product;
    }
    public int get_position() {
        return _position;
    }
    public String get_title() {
        return _title;
    }
    public int get_count() {
        return _count;
    }
    public float get_price() {
        return _price;
    }
    public float get_total() {
        return _total;
    }
    public TypeReceipt get_type() {
        return _type;
    }
}

package com.kudinov.restoratorclient.datawaiter;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderElement implements Parcelable{
    private Integer product_id;
    private Float price;
    private Integer count;

    public OrderElement(Integer product_id, Float price, Integer count) {
        this.product_id = product_id;
        this.price = price;
        this.count = count;
    }

    public OrderElement(Parcel in) {
      int[] data = new int[2];
      in.readIntArray(data);
      product_id = data[0];
      count = data[1];

      price =  in.readFloat();
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getProduct_id() {
        return product_id;
    }
    public Integer getCount() {
        return count;
    }
    public Float getPrice() {
        return price;
    }

    public static final Creator<OrderElement> CREATOR = new Creator<OrderElement>() {
        @Override
        public OrderElement createFromParcel(Parcel in) {
            return new OrderElement(in);
        }

        @Override
        public OrderElement[] newArray(int size) {
            return new OrderElement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(new int[]{product_id,count});
        parcel.writeFloat(price);
    }
}

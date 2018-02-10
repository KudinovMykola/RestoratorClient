package com.kudinov.restoratorclient.datawaiter;

import android.os.Parcel;
import android.os.Parcelable;

import com.kudinov.restoratorclient.item.OrderItem;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Table implements Parcelable{
    public final static int NON_CHECKED_POSITION = -1;

    private String name;

    private List<OrderElement> orderedList;
    private List<OrderElement> reserveList;
    private List<OrderElement> currentList;

    private int checkDepartmentPosition;
    private int[] arrCheckCategory;
    //private int checkCategoryPosition;

    public Table(String name, int sizeDepartmentList) {
        this.name = name;

        orderedList = new ArrayList<>();
        reserveList = new ArrayList<>();
        currentList = new ArrayList<>();

        checkDepartmentPosition = NON_CHECKED_POSITION;
        arrCheckCategory = new int[sizeDepartmentList];
        for(int i = 0; i < sizeDepartmentList; i++)
            arrCheckCategory[i] = NON_CHECKED_POSITION;

    }

    protected Table(Parcel in) {
        name = in.readString();
        List<OrderElement> allElements = in.createTypedArrayList(OrderElement.CREATOR);

        orderedList = new ArrayList<>();
        reserveList = new ArrayList<>();
        currentList = new ArrayList<>();

        int pos = 0;
        OrderElement temp = allElements.get(pos);
        while(temp != null) {
            orderedList.add(temp);
            pos++;
            temp = allElements.get(pos);
        }

        pos++;
        temp = allElements.get(pos);
        while(temp != null) {
            reserveList.add(temp);
            pos++;
            temp = allElements.get(pos);
        }

        pos++;
        while(pos < allElements.size()) {
            currentList.add(allElements.get(pos));
            pos++;
        }

        checkDepartmentPosition = in.readInt();
        arrCheckCategory = in.createIntArray();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    //support listwork
    public OrderElement findOrderElementById(List<OrderElement> itemList, Integer id) {
        if(itemList.size() == 0)
            return null;

        for(OrderElement item: itemList) {
            if(item.getProduct_id().equals(id)) {
                return item;
            }
        }
        return null;
    }
    private void addOneElement(List<OrderElement> itemList, OrderElement element) {
        OrderElement item = findOrderElementById(itemList, element.getProduct_id());
        if(item != null) {
            int goalCount = item.getCount() + element.getCount();
            item.setCount(goalCount);
            return;
        }

        itemList.add(element);
    }
    private void addListOrder (List<OrderElement> baseList, List<OrderElement> addedList) {
        if (addedList.size() == 0)
            return;

        if(baseList.size() == 0) {
            baseList.addAll(addedList);
        } else {
            for (OrderElement item: addedList) {
                addOneElement(baseList, item);
            }
        }
    }
    private float getSum(List<OrderElement> list) {
        float sum = 0f;
        for(OrderElement element: list)
            sum += (element.getPrice() * element.getCount());

        return sum;
    }

    //public listwork's method's
    public void addProductToCurrentList(Product product, int count) {
        OrderElement addedItem = new OrderElement(product.getId(),product.getPrice(),count);
        addOneElement(currentList, addedItem);
    }
    public void clearCurrentAndReserve() {
        reserveList.clear();
        currentList.clear();
    }
    public void currentToReserve() {
        addListOrder(reserveList,currentList);
        currentList.clear();
    }
    public void allToOrderList() {
        addListOrder(orderedList, currentList);
        currentList.clear();

        addListOrder(orderedList,reserveList);
        reserveList.clear();
    }

    //setter and getters
    public String getName() {
        return name;
    }

    public int getCheckDepartmentPosition() {
        return checkDepartmentPosition;
    }
    public void setCheckDepartmentPosition(int checkDepartmentPosition) {
        this.checkDepartmentPosition = checkDepartmentPosition;
    }
    public int[] getArrCheckCategory() {
        return arrCheckCategory;
    }

    public List<OrderElement> getOrderedList() {
        return orderedList;
    }
    public List<OrderElement> getReserveList() {
        return reserveList;
    }
    public List<OrderElement> getCurrentList() {
        return currentList;
    }

    public float getOrderedSum() {
        return getSum(this.orderedList);
    }
    public float getReserveSum() {
        return getSum(this.reserveList);
    }
    public float getCurrentSum() {
        return getSum(this.currentList);
    }
    public float getTotal() {
        float sum = getOrderedSum() + getReserveSum() + getCurrentSum();

        return sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        List<OrderElement> listForParcel = new ArrayList<>();
        listForParcel.addAll(orderedList);
        listForParcel.add(null);
        listForParcel.addAll(reserveList);
        listForParcel.add(null);
        listForParcel.addAll(currentList);

        parcel.writeInt(checkDepartmentPosition);
        parcel.writeIntArray(arrCheckCategory);
        parcel.writeString(name);
        parcel.writeTypedList(listForParcel);
    }
}

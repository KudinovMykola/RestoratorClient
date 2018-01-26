package com.kudinov.restoratorclient.item;

import com.kudinov.restoratorclient.model.Category;

public class CategoryListItem implements Checkable {

    private Category category;
    private Boolean flag;


    public CategoryListItem() {
        this(null,false);
    }
    public CategoryListItem(Category category) {
        this(category,false);
    }
    public CategoryListItem(Category category, Boolean flag) {
        this.category = category;
        this.flag = flag;
    }

    public Category getCategory() {
        return category;
    }

    public Boolean getFlag() {
        return flag;
    }

    @Override
    public Boolean isChecked() {
        return flag;
    }

    @Override
    public void check() {
        flag = true;
    }

    @Override
    public void unCheck() {
        flag = false;
    }
}

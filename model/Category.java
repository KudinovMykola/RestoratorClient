package com.kudinov.restoratorclient.model;

public class Category {

    private Integer id;
    private String name;
    private Integer department_id;

    public Category() {
    }
    public Category(Integer id, String name, Integer department_id) {
        this.id = id;
        this.name = name;
        this.department_id = department_id;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!id.equals(category.id)) return false;
        if (!name.equals(category.name)) return false;
        return department_id.equals(category.department_id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + department_id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department_id=" + department_id +
                '}';
    }
}

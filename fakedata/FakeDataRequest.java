package com.kudinov.restoratorclient.fakedata;

import com.kudinov.restoratorclient.model.Category;
import com.kudinov.restoratorclient.model.Department;
import com.kudinov.restoratorclient.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FakeDataRequest {

    private List<Department> department_list;
    private List<Category> category_list;
    private List<Product> product_list;

    private List<String> setToList(Set<String> set) {
        List<String> list = new ArrayList<String>();
        list.addAll(set);
        return list;
    }

    public FakeDataRequest() {
        department_list = new ArrayList<Department>();
        department_list.add(new Department(0,"Бар"));
        department_list.add(new Department(1,"Кухня"));
        department_list.add(new Department(2,"Бар Empty"));
        department_list.add(new Department(3,"Кухня Empty"));

        category_list = new ArrayList<Category>();
        category_list.add(new Category(0, "Вино", 0));
        category_list.add(new Category(1, "Пиво", 0));
        category_list.add(new Category(2, "Коктейли", 0));
        category_list.add(new Category(3, "Крепкие напитки", 0));
        category_list.add(new Category(4, "Безалкогольные", 0));
        category_list.add(new Category(5, "Горячие напитки", 0));

        category_list.add(new Category(6, "Закуски", 1));
        category_list.add(new Category(7, "Салаты", 1));
        category_list.add(new Category(8, "Первые блюда", 1));
        category_list.add(new Category(9, "Рыбные блюда", 1));
        category_list.add(new Category(10, "Мясные блюда", 1));
        category_list.add(new Category(11, "Десерт", 1));

        product_list = new ArrayList<Product>();
        product_list.add(new Product(0,"Пино Нуар", 85f,0));
        product_list.add(new Product(1,"Пино Гриджио",140f,0));
        product_list.add(new Product(2,"Мальбек",210f,0));
        product_list.add(new Product(3,"Саперави",185f,0));

        product_list.add(new Product(4,"Corona",35f,1));
        product_list.add(new Product(5,"Hoegarden",50f,1));
        product_list.add(new Product(6,"Leffe",50f,1));
        product_list.add(new Product(7,"Tuborg",25f,1));

        product_list.add(new Product(8,"Маргарита",75f,2));
        product_list.add(new Product(9,"Кир Рояль",90f,2));
        product_list.add(new Product(10,"Космополитен",65f,2));
        product_list.add(new Product(11,"Негрони",90f,2));

        product_list.add(new Product(12,"Jack Daniels",75f,3));
        product_list.add(new Product(13,"Jameson",75f,3));
        product_list.add(new Product(14,"El Hamador",70f,3));
        product_list.add(new Product(15,"Хортица",25f,3));

        product_list.add(new Product(16,"Coca Cola",20f,4));
        product_list.add(new Product(17,"Милкшейк",30f,4));
        product_list.add(new Product(18,"Сок Rich",20f,4));
        product_list.add(new Product(19,"Моршинская",20f,4));

        product_list.add(new Product(20,"Эспрессо",20f,5));
        product_list.add(new Product(21,"Американо",20f,5));
        product_list.add(new Product(22,"Каппучино",25f,5));
        product_list.add(new Product(23,"Чай",20f,5));

        product_list.add(new Product(24,"Карпаччо",85f,6));
        product_list.add(new Product(25,"Сырное плато",110f,6));
        product_list.add(new Product(26,"Капрезе",45f,6));

        product_list.add(new Product(27,"Цезарь",55f,7));
        product_list.add(new Product(28,"Греческий",60f,7));
        product_list.add(new Product(29,"С телятиной",70f,7));
        product_list.add(new Product(30,"Огород",40f,7));

        product_list.add(new Product(31,"Уха",80f,8));
        product_list.add(new Product(32,"Солянка",60f,8));
        product_list.add(new Product(33,"Грибной крем-суп",55f,8));
        product_list.add(new Product(34,"Минестроне",55f,8));

        product_list.add(new Product(35,"Лосось фисташка",110f,9));
        product_list.add(new Product(36,"Сибас под лимоном",80f,9));
        product_list.add(new Product(37,"Жаренный карасик",55f,9));

        product_list.add(new Product(38,"Стейк Рибай",95f,10));
        product_list.add(new Product(39,"Утиная грудка",75f,10));
        product_list.add(new Product(40,"Шашлык",80f,10));
        product_list.add(new Product(41,"Цыплёнок тапака",90f,10));

        product_list.add(new Product(42,"Чизкейк",25f,11));
        product_list.add(new Product(43,"Брауни",30f,11));
        product_list.add(new Product(44,"Ореховый торт",25f,11));
        product_list.add(new Product(45,"Тирамису",33f,11));
        product_list.add(new Product(46,"Шоколадный фондан",45f,11));
        product_list.add(new Product(47,"Естерхази",40f,11));
        product_list.add(new Product(48,"Тарталетка ягодная",20f,11));
        product_list.add(new Product(49,"Штрудель яблочный",30f,11));
        product_list.add(new Product(50,"Мороженое",30f,11));
        product_list.add(new Product(51,"Печенье к чаю",10f,11));

    }

    public List<Department> getAllDepartment() {
        return department_list;
    }
    public List<Category> getAllCategory() {
        return category_list;
    }
    public List<Product> getAllProduct() {
        return product_list;
    }

    public List<Category> getCategoryByDepartment(Department dp) {
        List<Category> result_list = new ArrayList<Category>();
        Integer department_id = dp.getId();

        for(Category someCategory: category_list) {
            if(someCategory.getDepartment_id() == department_id)
                result_list.add(someCategory);
        }

        return result_list;
    }
    public List<Product> getProductByCategory(Category category) {
        List<Product> result_list = new ArrayList<Product>();
        Integer category_id = category.getId();

        for(Product someProduct: product_list) {
            if(someProduct.getCategory_id() == category_id)
                result_list.add(someProduct);
        }

        return result_list;
    }
    public List<Product> getProductByDepartment(Department dp) {
        List<Product> result_list = new ArrayList<Product>();

        List<Category> categories = this.getCategoryByDepartment(dp);

        for(Category category: categories) {
            result_list.addAll(this.getProductByCategory(category));

        }
        return result_list;
    }

    public List<Category> getKitchenCategory() {
        Department dp = department_list.get(1);
        return getCategoryByDepartment(dp);
    }

}

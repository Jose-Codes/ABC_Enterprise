package ABC_Enterprise;

import java.util.ArrayList;

public class Database {
    ArrayList<Product> products;
    Product product;
    int index;
    boolean found;

    Database(){
        products = new ArrayList<Product>();
    }

    void search(String name) {
        found = false;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getProductName().equalsIgnoreCase(name)) {
                found = true;
                index = i;
                product = p;
            }
        }
    }
    void add(Product p){
            products.add(p);
        }
    Product remove(int i){
        return products.remove(i);
    }
    boolean isFound(){
        return found;
    }
    Product getProduct(){
        return product;
    }
    int getIndex(){
        return index;
    }
    int getSize(){
        return products.size();
    }
    boolean isEmpty(){
        return products.isEmpty();
    }
    ArrayList getProducts(){
        return products;
    }
}


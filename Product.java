package ABC_Enterprise;

import java.util.Date;

public class Product {
    Manufacturer manu;
    String prod_name;
    String manu_Date;
    int quantity;
    double price;

    Product(Manufacturer manfc, String prd_Name, int qnt, double price,String manu_Date){
        manu = manfc;
        prod_name = prd_Name;
        quantity = qnt;
        this.price = price;
        this.manu_Date = manu_Date;
    }
    public Manufacturer getManufacturer(){
        return manu;
    }
    public String getProductName(){
        return prod_name;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getPrice(){
        return price;
    }
    public void changePrice(double amt){
        price = amt;
    }
    public void reOrder(int amnt){
        quantity += amnt;
    }

    public void sell(int amnt){
        quantity -= amnt;
    }

    public String getManu_Date() {
        return manu_Date;
    }


}

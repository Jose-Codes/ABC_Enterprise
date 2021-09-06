package ABC_Enterprise;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.text.SimpleDateFormat;

public class TestEnterprise {

    public static void main (String args []) throws Exception {
        Database db = new Database(); // creates database for available products
        Database deleted = new Database(); // creates a database for deleted products.
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        Date now = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy");
        Date manu_date;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        boolean done = false;

        while(!done){
            int menu = GetData.getInt("\tABC Enterprise\n" + "\t" + df.format(now) + "\n"
                    + "\nPlease Choose From the Following:" + "\n1. Add new product\n2. Update Existing Product" +
                    "\n3. Delete a Product\n4. View Product Information\n5. Exit");

            switch (menu){
                case 1:
                    // make an address object
                    String street = GetData.getString("What is the street address of the manufacturer?");
                    String city =  GetData.getString("What is the city of the manufacturer?");
                    String state = GetData.getString("In which state is the manufacturer located in?");
                    String zip = GetData.getString("What is the zip code of the manufacturer");
                    Address addr = new Address(street, city, state,zip);

                    //make manufacturer object
                    String company =  GetData.getString("What is the name of the company manufacturing the product");
                    Manufacturer manu = new Manufacturer(addr,company);

                    // make a product object
                    String prod_name = GetData.getString("What is the name of the product?");
                    int quantity = GetData.getInt("What is the quantity of the product?");
                    double price = GetData.getDouble("What is the price of the product?");

                    String Mdate = GetData.getString("What date was is manufactured? (MM/dd/yyyy");
                    manu_date = date_format.parse(Mdate); // parse from users String input to a date object
                    String date = date_format.format(manu_date); // format it from a date back to a string

                    Product p = new Product(manu, prod_name, quantity, price, date);

                    db.add(p); // add the product to the ArrayList

                break;
                case 2: //update a product
                    prod_name = GetData.getString("What is the name of the product you would like to update?");
                    db.search(prod_name);

                    if(!db.isFound()){
                        JOptionPane.showMessageDialog(null, "The Product was not found");
                    }
                    else {
                        int option = GetData.getInt("Would you like to (1) Re-order the product (2) Make a sale\n" +
                                "(3) change the price of the product?");
                        switch (option) {
                            case 1:
                                int qnt = GetData.getInt("How many " + prod_name + " would you like to re-order");
                                if (qnt < 0) {
                                    JOptionPane.showInputDialog(null, "Invalid quantity amount, the quantity must be greater than 0");
                                } else {
                                    Product prd = db.getProduct();
                                    prd.reOrder(qnt);
                                    JOptionPane.showMessageDialog(null, "" + qnt + " " + prod_name + " have been re-ordered.");
                                }
                                break;
                            case 2:
                                int amount = GetData.getInt("How many " + prod_name + " would you like to sell");
                                Product pd = db.getProduct();
                                if (amount > pd.getQuantity()) {
                                    JOptionPane.showMessageDialog(null, "The sale exceeds the quantity amount, sale could not be made");
                                } else {
                                    pd.sell(amount);
                                    JOptionPane.showMessageDialog(null, "" + amount + " " + prod_name + " have been sold");
                                }
                                break;
                            case 3:
                                Product prod = db.getProduct();
                                double new_price = GetData.getDouble("What should the new price be?");
                                if (new_price < 0) {
                                    JOptionPane.showMessageDialog(null, "The price cannot be negative");
                                } else {
                                    prod.changePrice(new_price);
                                    JOptionPane.showMessageDialog(null, "The price has been set to $" + new_price);
                                }
                        }
                    }
                break;
                case 3: //delete a product
                    String pd_name = GetData.getString("What is the name of the product you would like to delete?");
                    db.search(pd_name);
                    if(!db.isFound()){
                        JOptionPane.showMessageDialog(null, "The product was not found");
                    }else{
                       Product del = db.remove(db.getIndex());
                       deleted.add(del);
                       JOptionPane.showMessageDialog(null, "The product " + pd_name + " has been deleted");
                    }
                break;
                case 4: // view products
                    int userInput = GetData.getInt("What information would you like to view?\n" +
                            "1. Single product\n2. Inventory Report\n3. All deleted products\n");
                        switch (userInput){
                            case 1:// single product
                                String pro_name = GetData.getString("What is the name of the product you would like to view?");
                                db.search(pro_name);
                                if(!db.isFound()){
                                    JOptionPane.showMessageDialog(null, "Product was not found");
                                }else{
                                    String s = "Name\tPrice\tQuantity\n" + "" + pro_name +"\t" + nf.format(db.getProduct().getPrice()) +
                                    "\t" + db.getProduct().getQuantity();
                                    display(s,pro_name + " Inventory", JOptionPane.INFORMATION_MESSAGE);
                                }
                            break;
                            case 2: // Inventory report
                                ArrayList current = db.getProducts();
                                if(current.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "The list is empty");
                                }else{
                                    String s = "Product\tDate\tQuantity\tPrice\tManufacturer\tState\n";
                                    for(int i = 0; i < current.size();i++){
                                        Product prod = (Product) current.get(i);
                                        s = s + prod.getProductName() + "\t" + prod.getManu_Date() +
                                                "\t" + prod.getQuantity() + "\t" + prod.getPrice() +
                                                "\t" + prod.getManufacturer().getCompanyName() + "\t" +
                                                 prod.getManufacturer().getAddress().getState() + "\n";
                                    }
                                    display(s,"Active     Products" , JOptionPane.INFORMATION_MESSAGE);
                                }
                            break;
                            case 3: // deleted products
                                ArrayList removed = deleted.getProducts();
                                if(removed.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "The list is empty");
                                }else{
                                String s = "Product\tDate\tManufacturer\n";
                                for(int i = 0; i < removed.size();i++){
                                    Product pod = (Product) removed.get(i);
                                    s = s + pod.getProductName() +"\t" + pod.getManu_Date() +
                                            "\t" + pod.getManufacturer().getCompanyName() + "\n";
                                }
                                display(s,"Deleted     Products" , JOptionPane.INFORMATION_MESSAGE);
                                }
                            break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid choice.");
                            break;
                        }
                break;
                case 5: //Exit
                    done = true;
                break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
                break;
            }

        }

    }
    static void display(String s, String heading, int MESSAGE_TYPE){
        JTextArea text = new JTextArea(s,10,40);
        JScrollPane pane = new JScrollPane(text);
        JOptionPane.showMessageDialog(null, pane,heading,MESSAGE_TYPE);
    }
}

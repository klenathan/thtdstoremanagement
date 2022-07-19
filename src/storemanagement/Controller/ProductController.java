package storemanagement.Controller;

import java.util.ArrayList;
import java.util.Scanner;

import storemanagement.Service.Helper;

public class ProductController  {
    static String productDataFile = "data/product.csv";
    static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
//        new ProductController();
//        addProduct();
//        updatePrice();
    }

    public ProductController() {
        String content = "";
        ArrayList<String[]> dataArr = Helper.readData(productDataFile);

        for (int i = 0; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            for (int j = 0; j < line.length - 1; j++) {
                content += line[j] + ",";
            }
            content += line[line.length - 1];
            content += "\n";
        }
        System.out.println(content);
    }

    public static boolean productNameValidate(String productName) {
        ArrayList<String[]> dataArr = Helper.readData(productDataFile);
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (productName.equalsIgnoreCase(line[1])) {
                return false;
            }
        }
        return true;
    }

    // 9. An admin can add a new product to the Store
    public static void addProduct() {
        try {
            System.out.println("Enter the Product Name: ");
            String productName = inp.nextLine();
            System.out.println("Enter the Category: ");
            String category = inp.nextLine();
            System.out.println("Enter the Product Price: ");
            long price = inp.nextLong();
            if (price < 0) {
                price = 0;
            }
            String dataAdd = productName + "," + category + "," + price;

            if (productNameValidate(productName)) {
                Helper.addData(productDataFile, dataAdd);
            } else {
                System.out.println("The product \"" + productName + "\" already exists in the store!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 10. An admin can update price for a particular product
    public static void updatePrice() {
        System.out.println("Enter the product name that you want to change its price: ");
        String productName = inp.nextLine();

        System.out.println("Enter the new price: ");
        long newPrice = inp.nextLong();

        if (!productNameValidate(productName)) {
            ArrayList<String[]> dataArr = Helper.readData(productDataFile);
            for (int i = 1; i < dataArr.size(); i++) {
                String[] line = dataArr.get(i);
                if (line[1].equalsIgnoreCase(productName)) {
                    Helper.deleteLine(productDataFile, line[0]);
                    String data = line[1] + "," + line[2] + "," + newPrice;
                    Helper.addData(productDataFile,data);
                }
            }
        } else {
            System.out.println("Product \"" + productName + "\" does not exist." );
        }
    }


}

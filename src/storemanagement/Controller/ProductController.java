package storemanagement.Controller;

import storemanagement.Service.Helper;

import java.util.*;

public class ProductController {
    String productDataFile = "data/product.csv";
    Scanner inp = new Scanner(System.in);
    ArrayList<String[]> dataArr = Helper.readData(productDataFile);

    public static void main(String[] args) {
        ProductController productController = new ProductController();
        productController.addProduct("product3", "test", 100000);
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
    }

    public void addNewProduct(String productId, String productName, String category, String price) {
        String newData = productId + "," + productName + "," + category + "," + price;
        Helper.addData(productDataFile, newData);
    }

    public void removeProduct(String productId) {
        Helper.deleteLine(productDataFile, productId);
    }

    public boolean productNameValidate(String productName) {
        ArrayList<String[]> dataArr = Helper.readData(productDataFile);
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (productName.equalsIgnoreCase(line[1])) {
                return false;
            }
        }
        return true;
    }

    public static long priceValidate(long price) {
        if (price < 0) {
            return 0;
        } else {
            return price;
        }
    }

    // 9. An admin can add a new product to the Store
    public void addProduct(String productName, String category, long price) {
//            System.out.println("Enter the product name: ");
//            String productName = inp.nextLine();
//            System.out.println("Enter the category: ");
//            String category = inp.nextLine();
//            System.out.println("Enter the product price: ");
//            long price = this.inp.nextLong();
            long finalPrice = priceValidate(price);
            String dataAdd = productName + "," + category + "," + finalPrice;

            if (productNameValidate(productName)) {
                System.out.println("Successfully added new product to the store!");
                Helper.addData(productDataFile, dataAdd);
            } else {
                System.out.println("The product \"" + productName + "\" already exists in the store!");
            }

    }

    // 10. An admin can update price for a particular product
    public void updatePrice(String productName, long newPrice) {
//        System.out.println("Enter the product name that you want to change its price: ");
//        String productName = inp.nextLine();
//
//        System.out.println("Enter the new price: ");
//        long newPrice = inp.nextLong();

        long finalPrice = priceValidate(newPrice);

        if (!productNameValidate(productName)) {
            for (int i = 1; i < dataArr.size(); i++) {
                String[] line = dataArr.get(i);
                if (line[1].equalsIgnoreCase(productName)) {
                    Helper.deleteLine(productDataFile, line[0]);
                    String data = line[1] + "," + line[2] + "," + finalPrice;
                    Helper.addData(productDataFile, data);
                }
            }
        } else {
            System.out.println("Product \"" + productName + "\" does not exist.");
        }
    }

    // 5. A customer can sort all products by product price
    public void sortProduct(String input) {
        ArrayList<String> priceArr = new ArrayList<>();
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            priceArr.add(line[3]);
        }

//        System.out.println("Enter A for ascending order or D for descending order: ");
//        String productName = inp.nextLine();

        if (input.equalsIgnoreCase("A")) {
            Collections.sort(priceArr);
        } else if (input.equalsIgnoreCase(("D"))) {
            Collections.sort(priceArr, Collections.reverseOrder());
        }

        for (int j = 0; j < priceArr.size(); j++) {
            for (int i = 0; i < dataArr.size(); i++) {
                String[] line = dataArr.get(i);
                if (line[3] == priceArr.get(j)) {
                    System.out.println(line[1] + ", " + line[2] + ", " + line[3]);
                }
            }
        }
    }

    // 4. A customer can search for all available products for a particular category
    public String searchProduct(String productName) {
//        System.out.println("Enter the product name you want to view: ");
//        String productName = inp.nextLine();
        String product = "";
        if (!productNameValidate(productName)) {
            for (int i = 0; i < dataArr.size(); i++) {
                String[] line = dataArr.get(i);
                if (productName.equalsIgnoreCase(line[1])) {
                    product =  "Product name: " + line[1] + "\nCategory: " + line[2] + "\nPrice: " + line[3];
                }
            }
        } else {
            product = "Product \"" + productName + "\" does not exist.";
        }
        return product;
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }
}

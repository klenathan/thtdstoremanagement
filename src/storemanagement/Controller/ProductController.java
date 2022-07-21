package storemanagement.Controller;

import storemanagement.Service.Helper;

import java.util.*;

public class ProductController {
    static String productDataFile = "data/product.csv";
    Scanner inp = new Scanner(System.in);
    ArrayList<String[]> dataArr = Helper.readData(productDataFile);

    public static void main(String[] args) {
        ProductController productController = new ProductController();
        productController.addProduct();
    }

    public ProductController() {
        StringBuilder content = new StringBuilder();
        ArrayList<String[]> dataArr = Helper.readData(productDataFile);

        for (String[] line : dataArr) {
            for (int j = 0; j < line.length - 1; j++) {
                content.append(line[j]).append(",");
            }
            content.append(line[line.length - 1]);
            content.append("\n");
        }
    }

    public void addNewProduct(String productId, String productName, String category, String price) {
        String newData = productId + "," + productName + "," + category + "," + price;
        Helper.addData(productDataFile, newData);
    }

    public void removeProduct(String productId) {
        Helper.deleteLine(productDataFile, productId);
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

    public static long priceValidate(long price) {
        if (price < 0) {
            return 0;
        } else {
            return price;
        }
    }

    // 9. An admin can add a new product to the Store
    public void addProduct() {
        try {
            System.out.println("Enter the Product Name: ");
            String productName = inp.nextLine();
            System.out.println("Enter the Category: ");
            String category = inp.nextLine();
            System.out.println("Enter the Product Price: ");
            long price = this.inp.nextLong();
            long finalPrice = priceValidate(price);
            String dataAdd = productName + "," + category + "," + finalPrice;

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
    public void updatePrice() {
        System.out.println("Enter the product name that you want to change its price: ");
        String productName = inp.nextLine();

        System.out.println("Enter the new price: ");
        long newPrice = inp.nextLong();
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
    public void sortProduct() {
        ArrayList<String> priceArr = new ArrayList<>();
        for (int i = 1; i < dataArr.size(); i++) {
            String[] price = dataArr.get(i);
            priceArr.add(price[3]);
        }
        Collections.sort(priceArr);
        for (String s : priceArr) {
            for (String[] line : dataArr) {
                if (Objects.equals(line[3], s)) {
                    System.out.println(line[1] + ", " + line[2] + ", " + line[3]);
                }
            }
        }
    }

    // 4. A customer can search for all available products for a particular category
    public void searchProduct() {
        System.out.println("Enter the product name you want to view: ");
        String productName = inp.nextLine();

        if (!productNameValidate(productName)) {
            for (String[] line : dataArr) {
                if (productName.equalsIgnoreCase(line[1])) {
                    System.out.println("Product name: " + line[1] + "\nCategory: " + line[2] + "\nPrice: " + line[3]);
                }
            }
        } else {
            System.out.println("Product \"" + productName + "\" does not exist.");
        }
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }
}

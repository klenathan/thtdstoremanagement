package storemanagement.Controller;

import storemanagement.Service.Helper;

import java.util.*;

public class ProductController {
    private static String productDataFile = "data/product.csv";
    private ArrayList<String[]> dataArr;

    public static void main(String[] args) {
        ProductController productController = new ProductController();
//        productController.listAllProduct();
//        productController.addProduct("product4", "test", 200000);
//        productController.sortProduct("a");
//        System.out.println(productController.searchProduct("product4"));
        productController.updatePrice("P4", 100000);
    }

    public ProductController() {
        this.dataArr = Helper.readData(productDataFile);
    }

    /**
     * This method validates the product name
     *
     * @param productName
     * @return true/false
     */
    public boolean productNameValidate(String productName) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (productName.equalsIgnoreCase(line[1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method validates the price
     *
     * @param price
     * @return true/false
     */
    public long priceValidate(long price) {
        if (price < 0) {
            return 0;
        } else {
            return price;
        }
    }

    /**
     * This method helps admin add new product to the store
     *
     * @param productName
     * @param category
     * @param price
     */
    public void addProduct(String productName, String category, long price) {

        long finalPrice = priceValidate(price);
        String dataAdd = productName + "," + category + "," + finalPrice;

        if (productNameValidate(productName)) {
            System.out.println("Successfully added new product to the store!");
            Helper.addData(productDataFile, dataAdd);
        } else {
            System.out.println("The product \"" + productName + "\" already exists in the store!");
        }
    }

    /**
     * This method helps admin update price for a particular product
     *
     * @param productID
     * @param newPrice
     */
    public void updatePrice(String productID, long newPrice) {
        long finalPrice = priceValidate(newPrice);
        if(Helper.getAllId(productDataFile).contains(productID)) {
            Helper.modifyField(productDataFile, productID, 3, String.valueOf(finalPrice));
        } else {
            System.out.println("The product ID \"" + productID + "\" does not exists in the store!");
        }

    }

    /**
     * This method helps customer sort the product in an ascending order or descending order
     *
     * @param input
     */
    // 5. A customer can sort all products by product price
    public void sortProduct(String input) {
        ArrayList<String> priceArr = new ArrayList<>();
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            priceArr.add(line[3]);
        }

//        System.out.println("Enter A for ascending order or D for descending order: ");
//        System.out.println("Enter \"A\" for ascending order or \"D\" for descending order: ");
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

    /**
     * This method helps customer to search for available products for a particular category
     *
     * @param productName
     * @return product
     */
    public String searchProduct(String productName) {
        // System.out.println("Enter the product name you want to view: ");
        // String productName = inp.nextLine();
        String product = "";
        if (!productNameValidate(productName)) {
            for (String[] line : dataArr) {
                if (productName.equalsIgnoreCase(line[1])) {
                    product = "Product name: " + line[1] + "\nCategory: " + line[2] + "\nPrice: " + line[3];
                }
            }
        } else {
            product = "Product \"" + productName + "\" does not exist.";
        }
        return product;
    }

    /**
     * This method is to list all the products in the store
     */
    public void listAllProduct() {
        Helper.listAll(productDataFile);
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }
}

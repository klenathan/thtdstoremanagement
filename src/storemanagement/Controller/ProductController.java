package storemanagement.Controller;

import storemanagement.Model.Product;
import storemanagement.Service.Helper;

import java.util.*;

public class ProductController {
    private final String productDataFile = "data/product.csv";
    private ArrayList<String[]> dataArr;

    public ProductController() {
        this.dataArr = Helper.readData(productDataFile);
    }

    public static void main(String[] args) {
        ProductController a = new ProductController();
        System.out.println(a.sortProduct("a"));
    }

    /**
     * This method validates the product name
     *
     * @param productName product name input in String
     * @return true false when product name is found
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
     * @param productName: new product's information
     * @param category
     * @param price
     */
    public void addProduct(String productName, String category, long price) {

        long finalPrice = priceValidate(price);
        String dataAdd = productName + "," + category + "," + finalPrice;

        if (productNameValidate(productName)) {
            Helper.addData(productDataFile, dataAdd);
            this.dataArr = Helper.readData(productDataFile);
            System.out.println("Successfully added new product to the store!");
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
        if (Helper.getAllId(productDataFile).contains(productID)) {
            Helper.modifyField(productDataFile, productID, 3, String.valueOf(finalPrice));
            this.dataArr = Helper.readData(productDataFile);
        } else {
            System.out.println("The product ID \"" + productID + "\" does not exists in the store!");
        }

    }

    /**
     * This method helps customer sort the product in an ascending order or
     * descending order
     *
     * @param input
     */
    // 5. A customer can sort all products by product price
    public ArrayList<String[]> sortProduct(String input) {
        ArrayList<String> priceArr = new ArrayList<>();
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            priceArr.add(line[3]);
        }

        if (input.equalsIgnoreCase("A")) {
            Collections.sort(priceArr);
        } else if (input.equalsIgnoreCase(("D"))) {
            Collections.sort(priceArr, Collections.reverseOrder());
        }

        ArrayList<String[]> res = new ArrayList<>();

        for (int j = 0; j < priceArr.size(); j++) {
            for (int i = 0; i < dataArr.size(); i++) {
                String[] line = dataArr.get(i);
                if (line[3] == priceArr.get(j)) {
                    res.add(dataArr.get(i));
                }
            }
        }
        return res;
    }

    /**
     * This method helps customer to search for available products for a particular
     * category
     *
     * @param productName
     * @return product
     */
    public String searchProduct(String productName) {
        String product = "";
        if (!productNameValidate(productName)) {
            for (String[] line : dataArr) {
                if (productName.equalsIgnoreCase(line[1])) {
                    product = Helper.green("Product name: ") + line[1] + "\n"
                            + Helper.green("Category: ") + line[2] + "\n"
                            + Helper.green("Price: ") + line[3];
                }
            }
        } else {
            product = "Product \"" + Helper.error(productName) + "\" does not exist.";
        }
        return product;
    }

    public boolean checkProductExist(String id) {
        return Helper.getAllId(this.productDataFile).contains(id);
    }

    public Product getProductDetails(String productId) {
        if (checkProductExist(productId)) {
            String[] lineData = Helper.getDataFromLine(productDataFile, productId);
            return new Product(lineData[0], lineData[1], lineData[2], Long.parseLong(lineData[3]));
        } else {
            return null;
        }
    }

    public HashSet<String> getAllCategory() {
        HashSet<String> res = new HashSet<>();
        for (int i = 1; i < this.dataArr.size(); i++) {
            res.add(this.dataArr.get(i)[2]);
        }
        return res;
    }

    public ArrayList<String[]> getAllFromCat(String category) {
        ArrayList<String[]> res = new ArrayList<>();
        for (int i = 0; i < dataArr.size(); i++) {
            if (this.dataArr.get(i)[2].equalsIgnoreCase(category)) {
                res.add(dataArr.get(i));
            }
        }
        return res;
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }
}

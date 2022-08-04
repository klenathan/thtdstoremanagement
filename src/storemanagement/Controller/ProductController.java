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

    /**
     * This method validates the product name
     *
     * @param productName: type String
     * @return true/false when product name exists or not
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
     * @param price type long
     * @return 0 if the price < 0, otherwise return price
     */
    public long priceValidate(long price) {
        if (price < 0) {
            return 0;
        } else {
            return price;
        }
    }

    /**
     * This method checks whether product exists or not
     * @param id: type String
     * @return true/false if product exists or not
     */
    public boolean checkProductExist(String id) {
        return Helper.getAllId(this.productDataFile).contains(id.toUpperCase());
    }

    /**
     * This method helps admin add new product to the store
     *
     * @param productName: type String
     * @param category: type String
     * @param price: type long
     */
    public String addProduct(String productName, String category, long price) {

        long finalPrice = priceValidate(price);
        String dataAdd = productName + "," + category + "," + finalPrice;
        String message;
        if (productNameValidate(productName)) {
            Helper.addData(productDataFile, dataAdd);
            this.dataArr = Helper.readData(productDataFile);
            message = Helper.green("Successfully added new product to the store!");
        } else {
            message = Helper.error("The product \"" + productName + "\" already exists in the store!");
        }
        return message;
    }

    /**
     * This method will delete the product based on the product ID
     * @param productID
     * @return
     */
    public String deleteProduct(String productID) {
        String message;
        if (checkProductExist(productID)) {
            Helper.deleteLine(productDataFile, productID.toUpperCase());
            message = Helper.green("Successfully deleted!");
        } else {
            message = Helper.error("The product ID \"" + productID + "\" does not exist!");
        }
        return message;
    }

    /**
     * This method helps admin update price for a particular product
     *
     * @param productID: type String
     * @param newPrice: type long
     */
    public String updatePrice(String productID, long newPrice) {
        long finalPrice = priceValidate(newPrice);
        String message;
        if (checkProductExist(productID)) {
            Helper.modifyField(productDataFile, productID, 3, String.valueOf(finalPrice));
            this.dataArr = Helper.readData(productDataFile);
            message = Helper.green("Successfully updated!");
        } else {
            message = Helper.error("The product ID \"" + productID + "\" does not exist!");
        }
        return message;
    }


    /**
     * This method helps customer sort the product list in ascending order or
     * descending order
     *
     * @param input: type String
     * @return res: type ArrayList<String[]>
     */
    public ArrayList<String[]> sortProduct(String input) {
        ArrayList<Long> priceArr = new ArrayList<>();
        for (int i = 1; i < dataArr.size(); i++) {
            priceArr.add(Long.parseLong(dataArr.get(i)[3]));
        }

        if (input.equalsIgnoreCase("A")) {
            Collections.sort(priceArr);
        } else if (input.equalsIgnoreCase(("D"))) {
            priceArr.sort(Collections.reverseOrder());
        }

        HashMap<String, String[]> a = new HashMap<>();
        ArrayList<String[]> res = new ArrayList<>();
        for (int i = 0; i < priceArr.size(); i++) {
            for (int j = 1; j < dataArr.size(); j++) {
                if (Long.parseLong(dataArr.get(j)[3]) == priceArr.get(i) && !a.containsKey(dataArr.get(j)[0])) {
                    a.put(dataArr.get(j)[0], dataArr.get(j));
                    res.add(a.get(dataArr.get(j)[0]));
                }
            }
        }
        return res;
    }

    /**
     * This method helps customer to search for available product by name
     * category
     *
     * @param productName: type String
     * @return product: type String
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
            product = Helper.error("Product \"" + productName + "\" does not exist.");
        }
        return product;
    }

    /**
     * This method is to get all product detail by product id
     * @param productId String
     * @return new Product if product exist, otherwise return null: type Product
     */
    public Product getProductDetails(String productId) {
        if (checkProductExist(productId)) {
            String[] lineData = Helper.getDataFromLine(productDataFile, productId);
            return new Product(lineData[0], lineData[1], lineData[2], Long.parseLong(lineData[3]));
        } else {
            return null;
        }
    }

    /**
     * This method is to list all categories
     * @return res: type HashSet<String>
     */
    public HashSet<String> getAllCategory() {
        HashSet<String> res = new HashSet<>();
        for (int i = 1; i < this.dataArr.size(); i++) {
            res.add(this.dataArr.get(i)[2]);
        }
        return res;
    }

    /**
     * This method is to get all products by category
     * @param category: type String
     * @return res: type ArrayList<String[]>
     */
    public ArrayList<String[]> getAllFromCat(String category) {
        ArrayList<String[]> res = new ArrayList<>();
        for (String[] strings : dataArr) {
            if (strings[2].equalsIgnoreCase(category)) {
                res.add(strings);
            }
        }
        return res;
    }

    /**
     * This method is to get all products
     * @return dataArr: type ArrayList<String[]>
     */
    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }
}
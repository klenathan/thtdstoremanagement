package storemanagement.Controller;

import java.util.ArrayList;

import storemanagement.Model.Product;
import storemanagement.Service.Helper;

public class ProductController  {
    String dataFile = "data/product.csv";
    ArrayList<Product> productArray = new ArrayList<>();

    public static void main(String[] args) {
        new ProductController();
    }

    public ProductController() {
        getAllProduct();
    }

    public ArrayList<Product> getAllProduct() {
        ArrayList<String[]> dataArr = Helper.readData(dataFile);
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            productArray.add(new Product(line[0], line[1], line[2], line[3]));
//            System.out.println(line[0] + "|" + line[1] + "|" + line[2] + "|" + line[3]);
//            System.out.println("-".repeat(3 + line[0].length()+line[1].length()+line[2].length()+line[3].length()));
        }
        return this.productArray;
    }
    public void addNewProduct(String productId, String productName, String category, String price) {
        String newData = productId + "," + productName + "," + category + "," + price;
        Helper.addData(dataFile, newData);
    }

    public void removeProduct(String productId) {
        Helper.deleteLine(dataFile, productId);
    }

    public void modifyProductPrice(String productId, String newPrice) {

    }
}

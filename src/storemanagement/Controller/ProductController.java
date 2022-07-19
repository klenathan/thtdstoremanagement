package storemanagement.Controller;

import java.util.ArrayList;

import storemanagement.Service.Helper;

public class ProductController  {
    String dataFile = "data/product.csv";
//    public static void main(String[] args) {
//        new ProductController();
//    }

    public ProductController() {
        String content = "";
        ArrayList<String[]> dataArr = Helper.readData(dataFile);

        for (int i = 0; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            for (int j = 0; j < line.length - 1; j++) {
                content += line[j] + ", ";
            }
            content += line[line.length - 1];
            content += "\n";
        }
        System.out.println(content);
    }
}

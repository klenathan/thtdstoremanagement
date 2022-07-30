import storemanagement.Controller.*;
import storemanagement.Service.*;
import storemanagement.View.*;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        OrderController order = new OrderController();

        System.out.println(Arrays.toString(Helper.getDataFromLine("data/order.csv", "O1")));

        int counter = 0;
        while (counter < 100) {
            counter ++;
            Helper.addData("testFile.csv", "qwe,qwe");
            System.out.println("Added " + counter);
        }

        System.out.println("Done");
    }
}

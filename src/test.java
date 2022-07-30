import storemanagement.Controller.*;
import storemanagement.Service.*;
import storemanagement.View.*;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        OrderController order = new OrderController();
        System.out.println(Arrays.toString(Helper.getDataFromLine("data/order.csv", "O1")));

    }
}

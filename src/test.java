import storemanagement.Controller.*;
import storemanagement.Service.*;
import storemanagement.View.*;

public class test {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        OrderController order = new OrderController();
        Menu menu = new Menu("test");
        menu.tableDisplay(order.getDataArr());
        menu.tableDisplay(productController.getDataArr());
    }
}

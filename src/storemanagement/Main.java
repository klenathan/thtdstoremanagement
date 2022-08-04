package storemanagement;

import storemanagement.Controller.AccountController;
import storemanagement.Controller.OrderController;
import storemanagement.Controller.ProductController;

import storemanagement.View.Menu;

public class Main {
    public static void main(String[] args) {
        // DONG - HA: TODO: 03/08/2022 Add products to database

        // Thai: TODO: 03/08/2022 : Revenue on that day, info of orders on that day

        ProductController productController = new ProductController();
        AccountController accountController = new AccountController();
        OrderController orderController = new OrderController();
        Menu menu = new Menu(productController, accountController, orderController);
        menu.run();
    }
}

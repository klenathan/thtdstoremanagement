package storemanagement;

import storemanagement.Controller.AccountController;
import storemanagement.Controller.OrderController;
import storemanagement.Controller.ProductController;

import storemanagement.View.Menu;

public class Main {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        AccountController accountController = new AccountController();
        OrderController orderController = new OrderController();
        new Menu(productController, accountController, orderController);
    }
}

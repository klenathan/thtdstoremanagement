import storemanagement.Controller.AccountController;
import storemanagement.Controller.OrderController;
import storemanagement.Service.Helper;

import java.io.PrintStream;

public class test {
    public static void main(String[] args) {
//        OrderController a = new OrderController();
//        System.out.println(a.membershipDiscount("u1"));

        OrderController o = new OrderController();
        o.createOrder("P2", "u1", 32,123120);
        AccountController a = new AccountController();
        System.out.println(a.totalPayment("u1"));
    }
}

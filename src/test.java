import storemanagement.Controller.AccountController;
import storemanagement.Controller.OrderController;

public class test {
    public static void main(String[] args) {
//        OrderController a = new OrderController();
//        System.out.println(a.membershipDiscount("u1"));
        AccountController a = new AccountController();
        a.membership("admin");
//        a.addTotalPayment("admin");
    }
}

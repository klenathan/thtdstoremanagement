import storemanagement.Controller.AccountController;
import storemanagement.Service.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
        AccountController a = new AccountController();
        if (a.login("admin", "ádad")) {
            System.out.println("success");
        }
    }
}

import storemanagement.Controller.AccountController;
import storemanagement.Service.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
        AccountController a = new AccountController();
        System.out.println(a.signup("Pham Vo Dong", " ", "dong", "0392612231"));
        }

}

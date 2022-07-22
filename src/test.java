import storemanagement.Controller.AccountController;
import storemanagement.Service.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
        AccountController a = new AccountController();
        System.out.println(a.signup("Pham Vo Dong", " ", "Dong", "12312312"));
//        System.out.println(a.signupUsernameValidate(" "));
    }
}

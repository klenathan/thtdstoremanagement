import storemanagement.Controller.*;
import storemanagement.Service.*;
import storemanagement.View.*;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
       AccountController a = new AccountController();
        System.out.println(a.phoneValidate(" 12231"));
    }
}

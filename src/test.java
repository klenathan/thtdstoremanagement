import storemanagement.Controller.*;
import storemanagement.Service.*;
import storemanagement.View.*;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
      AccountController a = new AccountController();
      a.setRole("U3", "admin");
        String userDataFile = "data/user.csv";
        System.out.println(Helper.getAllId(userDataFile));
//      OrderController o = new OrderController();
//      o.membershipCheck("U3");
    }
}

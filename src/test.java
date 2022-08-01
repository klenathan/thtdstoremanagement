import storemanagement.Controller.*;
import storemanagement.Model.Account;
import storemanagement.Service.*;
import storemanagement.View.*;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
    AccountController a = new AccountController();
    System.out.println(a.userViewInformation("admin"));
}}
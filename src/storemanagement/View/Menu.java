package storemanagement.View;
import storemanagement.Controller.AccountController;
import storemanagement.Controller.ProductController;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    ProductController productController = new ProductController();
    AccountController accController;
    public static void main(String[] args) {
        new Menu();
    }

    public Menu() {
        welcomeScreen();


        System.out.println(accController.getAccount().getFullName());;
    }

    public void welcomeScreen() {
        System.out.println("""
                COSC2081 GROUP ASSIGNMENT
                STORE ORDER MANAGEMENT SYSTEM
                Instructor: Mr. Minh Vu
                Group: Group 7
                s3878246, Pham Anh Thu
                s3891890, Nam Thai Tran
                S3891968, Pham Vo Dong
                """);
        int input = userOption();
        switch (input) {
            case 0 -> System.out.println("Thank you for visiting our store! Hope to see you again!");
            case 1 -> {
                System.out.println("1. List all products");
                new ProductController();
            }
            case 2 -> {
                System.out.println("2.Search product by name");
//                productController.searchProduct();
            }
            case 3 -> {
                System.out.println("3. Log in your account");
                accController = new AccountController();
                accController.login();
            }
            case 4 -> {
//                System.out.println("4. Sign up your account");
                accController = new AccountController();
                accController.signup();
            }
            case 5 -> System.out.println("5. Admin login");
        }
    }
    public static int userOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose one of these options:\n" +
                "0. Exit\n" +
                "1. List all products               2. Search item by name\n" +
                "3. Log in your account             4. Sign up your account\n" +
                "5. Admin login");

        Integer[] optionArr = {0, 1, 2, 3, 4, 5};
        int n;
        while (true) {
            try {
                do {
                    System.out.print("Enter one of the options above:\n");
                    n = input.nextInt();
                } while (!Arrays.asList(optionArr).contains(n));
                return n;
            } catch (Exception e) {
                input.next();
                System.out.print("Invalid Input! Please enter again! ");
            }
        }
    }
}


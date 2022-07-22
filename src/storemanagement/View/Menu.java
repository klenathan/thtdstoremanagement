package storemanagement.View;

import storemanagement.Controller.*;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    ProductController productController;
    AccountController accController;
    OrderController orderController;

    public static void main(String[] args) {
        Menu menu = new Menu();

    }

    public Menu() {
        this.accController = new AccountController();
        this.productController = new ProductController();
        this.orderController = new OrderController();
        welcomeScreen();
    }

//    public Menu(String hello) {
//        System.out.println("test");
//        inputLogin();
//    }

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
        int input;
        boolean runMenu = true;
        try {
            while (true) {
                input = userOption();
                if (input == 0){
                    System.out.println("Thank you for visiting our store! Hope to see you again!");
                    break;
                } else if (input == 1) {
                    System.out.println("1. List all products");
                    new ProductController();
                } else if (input == 2) {
                    System.out.println("2.Search product by name");
                    // productController.searchProduct();
                }else if (input == 3){
                    System.out.println("Please type in your account");
                    inputLogin();
                }else if (input == 4){
                    System.out.println("SIGN UP | Please type in your account");
                }else if (input == 5) {
                    System.out.println("5. Admin login");
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public int userOption() {

        String username = accController.getAccount() != null ? accController.getAccount().getUsername(): "";
        String optionsTxt = """
                ================================
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                --------------------------------
                3. Log in your account
                4. Sign up your account
                5. Admin login
                ================================""";
        String optionTxtWithName = """
                ================================
                Welcome,"""+ " \u001B[1m\u001B[31m" + username + "\033[0;0m! " + """
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                --------------------------------
                3. Log in your account
                4. Sign up your account
                5. Admin login
                ================================""";

        if (accController.getAccount() != null) {
            System.out.println(optionTxtWithName);
        } else {
            System.out.println(optionsTxt);
        }

        Scanner input = new Scanner(System.in);
        Integer[] optionArr = { 0, 1, 2, 3, 4, 5 };
        int n;
        // TODO: 22/07/2022 Fix auto break loop after logged in :( 
        while (true) {
            System.out.print("Enter one of the options above: ");
            n = input.nextInt();
            if(!Arrays.asList(optionArr).contains(n)) {
                return n;
            } else {
                input.next();
                System.out.print("Invalid Input! Please enter again! ");
            }

//            try {
//                do {
//                    System.out.print("Enter one of the options above: ");
//                    n = input.nextInt();
//                } while (!Arrays.asList(optionArr).contains(n));
//                return n;
//            } catch (Exception e) {
//                input.next();
//                System.out.print("Invalid Input! Please enter again! ");
//            }
        }
    }

    public void inputLogin() {
        Scanner loginScan = new Scanner(System.in);
        String username;
        String password;
        System.out.print("Input username: ");
        username = loginScan.nextLine();
        System.out.print("Input password: ");
        password = loginScan.nextLine();
        loginScan.close();
//        boolean loginState = accController.login(username, password);
        if (accController.login(username, password)) {
            accController.setCurrentAccount(username);
            System.out.println("Login successfully!");
        } else {
            System.out.println("wrong username or password");
        }
        System.out.print("\n");
        loginScan.close();
    }
}

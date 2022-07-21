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
        welcomeScreen();
        accController = new AccountController();
         productController = new ProductController();
         orderController = new OrderController();
    }

    public Menu(String hello) {
        System.out.println(hello);
        inputLogin();
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
        boolean runMenu = true;
        try {
            while (runMenu) {
                switch (input) {
                    case 0 -> {
                        System.out.println("Thank you for visiting our store! Hope to see you again!");
                        runMenu = false;
                    }
                    case 1 -> {
                        System.out.println("1. List all products");
                        new ProductController();
                    }
                    case 2 -> {
                        System.out.println("2.Search product by name");
                        productController.searchProduct();
                    }
                    case 3 -> {
                        System.out.println("3. Log in your account");
                        inputLogin();
                    }
                    case 4 -> {
                    }
                    case 5 -> System.out.println("5. Admin login");
                }
            }
        }   catch(Exception e){
            e.getStackTrace();
        }
    }
    public int userOption() {
        Scanner input = new Scanner(System.in);
        String options = """
        Choose one of these options:
        0. Exit
        1. List all products
        2. Search item by name
        --------------------------------
        3. Log in your account
        4. Sign up your account
        5. Admin login""";
        System.out.println(options);

        Integer[] optionArr = {0, 1, 2, 3, 4, 5};
        int n;
        while (true) {
            try {
                do {
                    System.out.print("Enter one of the options above: ");
                    n = input.nextInt();
                } while (!Arrays.asList(optionArr).contains(n));
                return n;
            } catch (Exception e) {
                input.next();
                System.out.print("Invalid Input! Please enter again! ");
            }
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
        accController.login(username, password);
//        loginScan.close();
        System.out.println(username + " " + password);
    }
}


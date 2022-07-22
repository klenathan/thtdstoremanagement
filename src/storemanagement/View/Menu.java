package storemanagement.View;

import storemanagement.Controller.*;

import java.util.Arrays;
import java.util.InputMismatchException;
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
        if (accController.getAccount() != null) {
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
                    }else if (input == 3){
                        System.out.println("================================");
                        System.out.println("Please type in your account");
                        this.inputLogin();
                    }else if (input == 4){
                        System.out.println("4 is now blank");
                    }else if (input == 5) {
                        System.out.println("5 is blank");
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e);
            }
        } else {
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
                    }else if (input == 3){
                        System.out.println("================================");
                        System.out.println("Please type in your account");
                        this.inputLogin();
                    }else if (input == 4){
                        System.out.println("SIGN UP | Please type in your account");
                        this.inputLogin();
                    }else if (input == 5) {
                        System.out.println("5. Admin login");
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e);
            }
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
                Welcome,"""+ Coloring.GREEN + Coloring.BLACK_BACKGROUND + username + "\033[0;0m! " + """
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                --------------------------------
                3. Log in your account
                4. BLANK
                5. BLANK
                ================================""";

        if (accController.getAccount() != null) {
            System.out.println(optionTxtWithName);
        } else {
            System.out.println(optionsTxt);
        }
        Integer[] optionArr = { 0, 1, 2, 3, 4, 5 };

        while (true) {
            Scanner input = new Scanner(System.in);
            int n;
            try {
                do {
                    System.out.print("Enter one of the options above: ");
                    n = input.nextInt();
                } while (!Arrays.asList(optionArr).contains(n));
                return n;
            } catch (Exception e) {
                input.next();
                System.out.println(error("invalid Input! Please try again"));
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
        if (accController.login(username, password)) {
            accController.setCurrentAccount(username);
            System.out.println("Login successfully!");
        } else {
            System.out.println(this.error("WRONG USERNAME OR PASSWORD"));
        }
        System.out.print("\n");
    }

    private String error(String message) {
        return Coloring.RED + message + Coloring.RESET;
    }
}

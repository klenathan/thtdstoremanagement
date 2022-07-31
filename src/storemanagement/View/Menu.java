package storemanagement.View;

import storemanagement.Controller.*;
import storemanagement.Model.Product;

import java.util.*;

public class Menu {

    private ProductController productController;
    private AccountController accController;
    private OrderController orderController;

    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String BLACK_BACKGROUND = "\u001B[40m";
    private final String RESET = "\u001B[0m";

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
        while (true) {
            if (accController.getAccount() != null && accController.getAccount().getRole().equalsIgnoreCase("admin")) {
                //ADMIN MENU
                try {
                    input = userOption();
                    Scanner adminScan = new Scanner(System.in);
                    if (input == 0) {
                        System.out.println("Program exited");
                        break;
                    } else if (input == 1) {
                        System.out.println("PRODUCT LIST | List all product from the store");
                        this.tableDisplay(productController.getDataArr());
                        this.selectProduct();
                        System.out.println("");
                    } else if (input == 2) {
                        System.out.println("2.Search product by name | WORKING ");
                    } else if (input == 3) {
                        System.out.println("ORDERS LIST | Get all orders from user ID");
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill", "Order Status"};
                        ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
                        this.tableDisplay(headingArr);
                        this.tableDisplay(orderController.getCurrenUserOrders(adminScan.nextLine()));
                    } else if (input == 4) {
                        System.out.print("New product name: ");
                        String productName = adminScan.nextLine();
                        System.out.print("New product category: ");
                        String category = adminScan.nextLine();
                        System.out.print("New product price: ");
                        long price = Long.parseLong(adminScan.nextLine());
                        productController.addProduct(productName, category, price);
                    } else if (input == 5) {
                        System.out.print("Input product ID: ");
                        String productId = adminScan.nextLine();
                        System.out.print("New product price: ");
                        long price = Long.parseLong(adminScan.nextLine());
                        productController.updatePrice(productId, price);
                    } else if (input == 6) {
                        System.out.println("Please input order ID: ");
                        String orderId = adminScan.nextLine();
                        orderController.updateOrderStatus(orderId);
                    }else if (input == 7){
                        accController.listAllUser();
                    } else if (input == 8) {
                        System.out.println("Please input customer ID");
                        String cusID = adminScan.nextLine();
                        System.out.println("PLease input the role of this customer (admin or user)");
                        String role =  adminScan.nextLine();
                        accController.setRole(cusID, role);
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    System.out.println(e);
                }
            }else if (accController.getAccount() != null) {
                // LOGGED IN MENU
                Scanner scan = new Scanner(System.in);
                try {
                    input = userOption();
                    if (input == 0) {
                        System.out.println("Thank you for visiting our store! Hope to see you again!");
                        break;
                    } else if (input == 1) {
                        System.out.println("\nPRODUCT LIST | List all product from the store");
                        this.tableDisplay(productController.getDataArr());
                        this.selectProduct();
                        System.out.println("");
                    } else if (input == 2) {
                        System.out.println("2.Search product by name");
                    } else if (input == 3) {
                        System.out.println("ORDERS LIST | This is your order list");
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill", "Order Status"};
                        ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
                        this.tableDisplay(headingArr);
                        this.tableDisplay(orderController.getCurrenUserOrders(accController.getAccount().getUserId()));
                        System.out.println("");
                    } else if (input == 4) {
                        System.out.println("4. Get order information");
                        System.out.print("Please input order ID: ");
                        String orderId = scan.nextLine();

                        ArrayList<String[]> res = new ArrayList<>();
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill", "Order Status"};
                        res.add(heading);
                        res.add(orderController.getOrderInfo(orderId));
                        this.tableDisplay(res);
                    } else if (input == 5) {
                        System.out.println("5 is blank");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(error("Please input a number"));
                }  catch (Exception e) {
                    e.getStackTrace();
                    System.out.println(e);
                }
            } else {
                // GUEST MENU
                try {
                    input = userOption();
                    if (input == 0){
                        System.out.println("Thank you for visiting our store! Hope to see you again!");
                        break;
                    } else if (input == 1) {
                        System.out.println("\nPRODUCT LIST | Please Login to place orders");
                        this.tableDisplay(productController.getDataArr());
                        System.out.println("");
                    } else if (input == 2) {
                        System.out.println("2.Search product by name");
                    }else if (input == 3){
                        System.out.println("================================");
                        System.out.println("LOGIN | Please type in your account");
                        this.inputLogin();
                    }else if (input == 4){
                        System.out.println("SIGN UP | Please type in your information");
                        this.inputSignup();
                    }else if (input == 5) {
                        System.out.println("5. Admin login");
                    }

                } catch (Exception e) {
                    e.getStackTrace();
                    System.out.println(e);
                }
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
                3. Log in your account
                4. Sign up your account
                5. Admin login
                ================================""";
        String optionTxtWithName = """
                ================================
                Welcome,"""+ GREEN + BLACK_BACKGROUND + username + RESET + "! " + """
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                3. List my orders
                4. Get order information
                5. BLANK
                ================================""";

        String adminOpttxt = """
                ================================
                Welcome to admin menu,"""+ GREEN + BLACK_BACKGROUND + username + RESET + "! " + """
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                3. List user's orders from userID
                4. Add new product
                5. Change product price
                6. Get order detail by orderID
                7. List all user information
                8. Set role for account""";

        if (accController.getAccount() != null && accController.getAccount().getRole().equalsIgnoreCase("admin")) {
            System.out.println(adminOpttxt);
        } else if (accController.getAccount() != null) {
            System.out.println(optionTxtWithName);
        } else {
            System.out.println(optionsTxt);
        }
        Integer[] optionArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8};

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

    private void inputLogin() {
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

    private void inputSignup() throws Exception {
        Scanner signupScan = new Scanner(System.in);
        System.out.print("Enter your username (username cannot contain space): ");
        String username = signupScan.nextLine();
        System.out.print("Enter your password: ");
        String password = signupScan.nextLine();
        System.out.print("Enter your full name: ");
        String fullName = signupScan.nextLine();
        System.out.print("Enter your phone number: ");
        String phoneNum = signupScan.nextLine();

        if (accController.signup(fullName, username, password, phoneNum)) {
            accController.setCurrentAccount(username);
            System.out.println(this.green("Sign up successfully!"));
        } else {
            System.out.println(this.error("USERNAME EXIST OR INVALID USERNAME"));
        }
        System.out.print("\n");
    }

    private void selectProduct() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input product ID to create order or \"0\" to escape: ");
        String userInput = scan.nextLine().toUpperCase();

        if (productController.checkProductExist(userInput)) {
            System.out.print("How many do you want to get? ");
            int quantity = Integer.parseInt(scan.nextLine());
            Product targetProduct = productController.getProductDetails(userInput);
            long price = targetProduct.getPrice();
            orderController.createOrder(userInput, accController.getAccount().getUserId(), quantity, price);
            System.out.println("You ordered: " + green(String.valueOf(quantity)) + " * " + green(targetProduct.getProductName())
                    + " for " + green(String.valueOf(quantity * price)) + " VND");
            System.out.println("Order created! Thank you for ordering from us!");
        } else if (userInput.equalsIgnoreCase("0")) {
            System.out.println("");
        } else {
            System.out.println("Failed to find desired product, please try again");
        }
    }

    private void tableDisplay(ArrayList<String[]> displayData) {
        int colWidth = 15;
        for (int i = 0; i < displayData.size(); i++) {
            String[] line = displayData.get(i);
            for (int j = 0; j < line.length; j++) {
                if (line[j].length() > 15) {
                    line[j] = line[j].substring(0, Math.min(line[j].length(), 12)) + "...";
                }
                System.out.print(" " + line[j] + " ".repeat(colWidth-line[j].length()) + "||");
            }
            System.out.print("\n" + "=".repeat((colWidth + 3)* line.length ));
            System.out.print("\n");
        }
    }

    private String error(String message) {
        return RED + message + RESET;
    }

    private String green(String mes) {
        return GREEN + mes + RESET;
    }
}

















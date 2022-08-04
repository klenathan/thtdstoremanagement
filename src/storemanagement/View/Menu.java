package storemanagement.View;

import storemanagement.Controller.*;
import storemanagement.Model.Product;
import storemanagement.Service.Helper;

import java.util.*;

public class Menu {
    private ProductController productController;
    private AccountController accController;
    private OrderController orderController;

    public Menu(ProductController productController, AccountController accController, OrderController orderController) {
        this.productController = productController;
        this.accController = accController;
        this.orderController = orderController;
    }

    /**
     * This method prints the screen based on user input
     */
    public void run() {
        System.out.println("""
                COSC2081 GROUP ASSIGNMENT
                STORE ORDER MANAGEMENT SYSTEM
                Instructor: Mr. Minh Vu
                Group: Group 7
                s3878246, Pham Anh Thu
                s3891890, Nam Thai Tran
                S3891968, Pham Vo Dong
                s3928433, Ha Viet Bui
                """);
        int input;
        while (true) {
            // Common menu
            try {
                input = userOption();
                Scanner scan = new Scanner(System.in);
                if (input == 0) {
                    System.out.println("Thank you for using our application!");
                    break;
                } else if (input == 1) {
                    System.out.println("PRODUCT LIST | List all product from the store");
                    this.tableDisplay(productController.getDataArr());
                    System.out.println(
                            "Do you want to sort the product list by price? Press \"Y\" for YES or \"enter/return\" for NO");
                    String inp = scan.nextLine();
                    this.productSort(inp);
                    if (accController.getAccount() != null) {
                        this.selectProduct();
                    }
                } else if (input == 2) {
                    System.out.println("SEARCH PRODUCT | Search a product from the store by its name ");
                    System.out.print("Input product name: ");
                    String productName = scan.nextLine();
                    System.out.println(productController.searchProduct(productName));
                } else if (input == 3) {
                    System.out.println("CATEGORY VIEW | View by category");
                    boolean checkInp = this.categoryView();
                    if (accController.getAccount() != null && checkInp) {
                        this.selectProduct();
                    }
                }
                if (accController.getAccount() != null && accController.getAccount().getRole().equalsIgnoreCase("admin")) {
                    // ADMIN MENU
                    if (input == 4) {
                        System.out.println("ORDERS LIST | Get all orders from user ID");
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill",
                                "Order Status"};
                        ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
                        System.out.print("Input desired user ID: ");
                        String userId = scan.nextLine();
                        this.tableDisplay(headingArr);
                        this.tableDisplay(orderController.getCurrenUserOrders(userId));
                    } else if (input == 5) {
                        System.out.println("NEW PRODUCT | Add new product to the store");
                        System.out.print("New product name: ");
                        String productName = scan.nextLine();
                        System.out.print("New product category: ");
                        String category = scan.nextLine();
                        System.out.print("New product price: ");
                        long price = Long.parseLong(scan.nextLine());
                        System.out.println(productController.addProduct(productName, category, price));
                    } else if (input == 6) {
                        System.out.println("DELETE EXISTING PRODUCT | Delete existing product in a store");
                        System.out.println("Input product ID: ");
                        String productId = scan.nextLine();
                        System.out.println(productController.deleteProduct(productId));
                    }
                    else if (input == 7) {
                        System.out.println("UPDATE PRODUCT PRICE | Modify product's price");
                        System.out.print("Input product ID: ");
                        String productId = scan.nextLine();
                        System.out.print("New product price: ");
                        long price = Long.parseLong(scan.nextLine());
                        System.out.println(productController.updatePrice(productId, price));
                    } else if (input == 8) {
                        System.out.println("UPDATE ORDER STATUS | Change order status (UNPAID -> PAID)");
                        System.out.print("Please input order ID: ");
                        String orderId = scan.nextLine();
                        System.out.println(orderController.updateOrderStatus(orderId.toUpperCase()));
                    } else if (input == 9) {
                        System.out.println("ALL USER INFO | List all user information");
                        this.tableDisplay(accController.getDataArr());
                        System.out.println("Enter to continue");
                        scan.nextLine();
                    } else if (input == 10) {
                        System.out.println("MODIFY ROLE | Modify a user's role");
                        System.out.println("Please input user ID: ");
                        String cusID = scan.nextLine();
                        System.out.print("PLease input the role of this user (admin or user): ");
                        String role = scan.nextLine();
                        System.out.println(accController.setRole(cusID, role));
                    } else if (input == 11) {
                        System.out.println("CUSTOMER INFORMATION | View customer information");
                        System.out.print("PLease input customer name: ");
                        String username = scan.nextLine();
                        System.out.println(accController.userViewInformation(username));
                        System.out.println("Enter to continue");
                        scan.nextLine();
                    }
                } else if (accController.getAccount() != null) {
                    // LOGGED IN MENU
                    if (input == 4) {
                        System.out.println("ORDERS LIST | This is your order list");
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill",
                                "Order Status"};
                        ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
                        this.tableDisplay(headingArr);
                        this.tableDisplay(orderController.getCurrenUserOrders(accController.getAccount().getUserId()));
                        System.out.println();
                    } else if (input == 5) {
                        System.out.println("ORDER INFORMATION | Get order's information");
                        System.out.print("Please input order ID: ");
                        String orderId = scan.nextLine();
                        ArrayList<String[]> res = new ArrayList<>();
                        String[] heading = {"Order ID", "Product ID", "User ID", "Quantity", "Total Bill",
                                "Order Status"};
                        res.add(heading);
                        res.add(orderController.getOrderInfo(orderId.toUpperCase(),
                                accController.getAccount().getUserId()));
                        this.tableDisplay(res);
                        System.out.println();
                    } else if (input == 6) {
                        System.out.println("USER PROFILE");
                        String username = accController.getAccount().getUsername();
                        System.out.println(accController.userViewInformation(username));
                    }
                } else {
                    // GUEST MENU
                    if (input == 4) {
                        System.out.println("================================");
                        System.out.println("LOGIN | Please type in your account");
                        this.inputLogin();
                    } else if (input == 5) {
                        System.out.println("SIGN UP | Please type in your information");
                        this.inputSignup();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(Helper.error("Please input a number"));
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e);
            }
        }
    }

    /**
     * This method gets the user input for the welcome screen
     * @return n (user input): type int
     */
    public int userOption() {
        String username = accController.getAccount() != null ? accController.getAccount().getFullName() : "";
        String commonTxt = """
                Choose one of these options:
                0. Exit
                1. List all products
                2. Search item by name
                3. List all categories
                """;
        String optionsTxt = """
                ================================================================
                """
                + commonTxt +
                """
                4. Log in your account
                5. Sign up your account
                ================================""";
        String optionTxtWithName = """
                ================================================================
                Welcome,""" + Helper.green(username) + "! " + """
                """
                + commonTxt +
                """
                4. List my orders
                5. Get order information
                6. View your profile
                ================================""";

        String adminOpttxt = """
                ================================================================
                Welcome to admin menu,""" + Helper.green(username) + "! " + """
                """
                + commonTxt +
                """
                4. List user's orders from userID
                5. Add new product
                6. Delete existing product
                7. Change product price
                8. Change order status (UNPAID -> PAID)
                9. List all user information
                10. Set role for account
                11. View customer information""";


        if (accController.getAccount() != null && accController.getAccount().getRole().equalsIgnoreCase("admin")) {
            System.out.println(adminOpttxt);
        } else if (accController.getAccount() != null) {
            System.out.println(optionTxtWithName);
        } else {
            System.out.println(optionsTxt);
        }
        Integer[] optionArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        while (true) {
            Scanner input = new Scanner(System.in);
            int n;
            try {
                do {
                    System.out.print("Enter one of the options above: ");
                    n = input.nextInt();
                    if (!Arrays.asList(optionArr).contains(n)) {
                        System.out.println(Helper.error("Invalid Input! Please try again"));
                    }
                } while (!Arrays.asList(optionArr).contains(n));
                return n;
            } catch (Exception e) {
                input.next();
                System.out.println(Helper.error("Invalid Input! Please try again"));
            }
        }
    }

    /**
     * This method asks for user input for login to the application
     */
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
            System.out.println(Helper.error("WRONG USERNAME OR PASSWORD"));
        }
        System.out.print("\n");
    }

    /**
     * This method asks for user input for signup to the application
     */
    private void inputSignup() {
        Scanner signupScan = new Scanner(System.in);
        System.out.print("Enter your username (username cannot contain space): ");
        String username = signupScan.nextLine();
        System.out.print("Enter your password: ");
        String password = signupScan.nextLine();
        System.out.print("Enter your full name: ");
        String fullName = signupScan.nextLine();
        System.out.print("Enter your phone number(10 digits): ");
        String phoneNum = signupScan.nextLine();
        if (accController.usernameValidate(username) || username.contains(" ")) {
            System.out.println(Helper.error("USERNAME EXIST OR INVALID USERNAME"));
        } else if (!accController.phoneValidate(phoneNum)) {
            System.out.println(Helper.error("INVALID PHONE NUMBER"));
        } else {
            accController.signup(fullName, username, password, phoneNum);
            accController.setCurrentAccount(username);
            System.out.println(Helper.green("Sign up successfully!"));
        }
        System.out.print("\n");
    }

    /**
     * This method selects the product and prints out the total bill
     */
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
            double discount = orderController.membershipDiscount(accController.getAccount().getUserId());
            int finalQuantity = orderController.quantityValidate(quantity);
            if (quantity < 0) {
                System.out.println(
                        "The quantity cannot be negative number. The quantity will automatically updated to 0.");
            }
            System.out.println("You have got " + Helper.green(discount * 100 + "% discount") + ".\nYou ordered: "
                    + Helper.green(String.valueOf(finalQuantity)) + " * " + Helper.green(String.valueOf(price))
                    + " * " + Helper.green(String.valueOf((1 - discount))) + " for "
                    + Helper.green(String.valueOf((long) (finalQuantity * price * (1 - discount)))) + " VND");
            System.out.println("Order created! Thank you for ordering from us!");
        } else if (userInput.equalsIgnoreCase("0")) {
            System.out.println();
        } else {
            System.out.println(Helper.error("Failed to find desired product, please try again"));
        }
    }

    /**
     * This method prints all the products in a chosen category number
     * @return
     */
    public boolean categoryView() {
        Scanner scan = new Scanner(System.in);
        int count = 1;

        HashMap<Integer, String> categoryMenuMap = new HashMap<>();
        for (String cat : productController.getAllCategory()) {
            System.out.println(count + ". " + cat);
            categoryMenuMap.put(count, cat);
            count++;
        }
        System.out.print("Choose desired category number or \"0\" to exit: ");
        String userInput = scan.nextLine();
        if (categoryMenuMap.containsKey(Integer.parseInt(userInput))) {
            String[] heading = {"ProductId", "ProductName", "Category", "Price"};
            ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
            this.tableDisplay(headingArr);
            this.tableDisplay(productController.getAllFromCat(categoryMenuMap.get(Integer.parseInt(userInput))));
            return true;
        } else {
            System.out.println(Helper.error("The category number \"" + userInput + "\" does not exist!"));
            return false;
        }
    }

    /**
     * This method prints out the sorted product list based on the user option
     * @param inp: type string
     */
    public void productSort(String inp) {
        Scanner scan = new Scanner(System.in);
        if (inp.equalsIgnoreCase("y")) {
            System.out.println("Press \"D\" for sorting in descending order or \"A\" for ascending order: ");
            String productOrder = scan.nextLine();
            String[] heading = {"ProductId", "ProductName", "Category", "Price"};
            ArrayList<String[]> headingArr = new ArrayList<>(Collections.singleton(heading));
            this.tableDisplay(headingArr);
            this.tableDisplay(productController.sortProduct(productOrder));
        }
    }

    /**
     * This method is to display the data as a table
     * @param displayData: type ArrayList<String[]>
     */
    private void tableDisplay(ArrayList<String[]> displayData) {
        int colWidth = 30;
        for (String[] line : displayData) {
            for (int j = 0; j < line.length; j++) {
                if (line[j].length() > colWidth) {
                    line[j] = line[j].substring(0, Math.min(line[j].length(), colWidth-3)) + "...";
                }
                System.out.print(" " + line[j] + " ".repeat(colWidth - line[j].length()) + "||");
            }
            System.out.print("\n" + "=".repeat((colWidth + 3) * line.length));
            System.out.print("\n");
        }
    }
}
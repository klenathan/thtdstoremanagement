package storemanagement.Controller;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {
    private final String welcomeTxt = """
            COSC2081 GROUP ASSIGNMENT
            STORE ORDER MANAGEMENT SYSTEM
            Instructor: Mr. Minh Vu
            Group: Group 7
            s3891890, Nam Thai Tran
            """;
    private final String mainMenuOpt = """
            1. Browse all products
            2. User login
            3. Exit
            """;
    public static void main(String[] args) {
        new MenuController();
    }

    Scanner scan = new Scanner(System.in);

    MenuController () {
        ProductController productController = new ProductController();
        System.out.println(welcomeTxt);
        while (true) {
            System.out.println(mainMenuOpt);
            String userInput = scan.nextLine();
            System.out.println("Chosen: " + userInput);

            if(userInput == "1") {
                ArrayList<String[]> dataArr = productController.getDataArr();
                for (int i = 0; i < dataArr.size(); i++) {
                    System.out.println();
                }

            }
        }
    }

    private void tableFormat() {

    }
}

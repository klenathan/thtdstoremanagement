package storemanagement.View;
import storemanagement.Controller.ProductController;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        System.out.println("COSC2081 GROUP ASSIGNMENT \n" +
                "STORE ORDER MANAGEMENT SYSTEM \n" +
                "Instructor: Mr. Minh Vu \n" +
                "Group: Group 7 \n" +
                "s3878246, Pham Anh Thu \n" +
                "sXXXXXXX, Student Name \n" +
                "sXXXXXXX, Student Name ");
        int input = userOption();
        switch (input) {
            case 0:
                System.out.println("Thank you for visiting our store! Hope to see you again!");
                break;
            case 1:
                System.out.println("1. List all products");
                new ProductController();
                break;
            case 2:
                System.out.println("2.Search item by name");
                break;
            case 3:
                System.out.println("3. Log in your account");
                break;
            case 4:
                System.out.println("4. Sign up your account");
                break;
            case 5:
                System.out.println("5. Admin login");
                break;
        }
    }

    public static int userOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose one of these options:\n" +
                "0. Exit\n" +
                "1. List all products               2. Search item by name\n" +
                "3. Log in your account             4. Sign up your account\n" +
                "5. Admin login");

        Integer optionArr[] = {0, 1, 2, 3, 4, 5};
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


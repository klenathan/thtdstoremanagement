package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountController {
    static String userDataFile = "data/user.csv";
    static Scanner input = new Scanner(System.in);
    static ArrayList<String[]> dataArr = Helper.readData(userDataFile);

    private Account account;

    public AccountController() {
        this.login();
    }

    public void login() {
        System.out.println("Enter your username:");
        String username = input.next();
        if (usernameValidate(username)) {
            System.out.println("Enter your password:");
            String password = input.next();
            String generatePass = hashPassword(password);
            if (passwordValidate(generatePass)) {
                System.out.println("Ok");
            } else {
                System.out.println("Wrong password, please try again!");
            }
        }
    }

    public void signup() {
        System.out.println("Enter your full name:");
        String fullName = input.nextLine();
        System.out.println("Enter your username:");
        String username = input.next();
        usernameValidate(username);
        System.out.println("Enter password:");
        String password = input.next();
        String generatePass = hashPassword(password);
        System.out.println("Enter phone number:");
        int phone = input.nextInt();
        String dataAdd = fullName + "," + username + "," + generatePass + "," + phone;
        Helper.addData(userDataFile, dataAdd);
    }

    public boolean usernameValidate(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordValidate(String password) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (password.equalsIgnoreCase(line[1])) {
                return false;
            }
        }
        return true;
    }

    public  String hashPassword(String password) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

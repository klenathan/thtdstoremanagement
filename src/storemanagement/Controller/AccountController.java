package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;
import storemanagement.View.Menu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountController {
    String userDataFile = "data/user.csv";
    Scanner input;
    ArrayList<String[]> dataArr = Helper.readData(userDataFile);
    Account account;

    public Account getAccount() {

        return account;
    }

    public AccountController() {
        input = new Scanner(System.in);
    }

    /**
     *This is the method help user login to our application
     */

    // TODO: 21/07/2022 login(String username, String password) -> boolean login status
    public void login() {
//        String username;
//        while (true) {
//            System.out.print("Enter your username: ");
//            username = input.next();
//            if (usernameValidate(username)) {
//                break;
//            } else {
//                System.out.println("""
//                        Account does not exist, please sign up!
//                        Do you want to create the account!(Y/N)""");
//                String option = input.next();
//                if (option.equalsIgnoreCase("Y")) {
//                    input.nextLine();
//                    signup();
//                } else {
//                    new Menu();
//                }
//            }
//        }
//        String password;
//        while (true) {
//            System.out.print("Enter your password: ");
//            password = input.next();
//            String generatePass = hashPassword(password);
//            if (passwordValidate(generatePass)) {
//                String[] userData = getUserData(username);
//                account = new Account(userData[1], userData[2], userData[3], userData[4], "none");
//                break;
//            } else {
//                System.out.println("Wrong password, please try again!");
//            }
//        }
    }

    /**
     * This method help user sign up our application
     */
    // TODO: 21/07/2022 signup(String username, String password) -> boolean login status
    public void signup() {
//        System.out.println("Enter your full name:");
//        String fullName = input.nextLine();
//
//        String username;
//        while (true) {
//            System.out.println("Enter your username:");
//            username = input.next();
//            if (usernameValidate(username)) {
//                System.out.println("This username was used, please try again!");
//            } else break;
//        }
//
//        System.out.println("Enter password:");
//        String password = input.next();
//        String generatePass = hashPassword(password);
//
//        System.out.println("Enter phone number (10 digits):");
//        String phone;
//        while (true) {
//            phone = input.next();
//            if (phone.length() == 10) {
//                break;
//            } else {
//                System.out.println("Invalid phone number, please try again!");
//            }
//        }
//        String dataAdd = username + "," + generatePass + "," + fullName + "," + phone;
//        Helper.addData(userDataFile, dataAdd);
    }

    /**
     * This method get user data
     * @return String[]
     */
    public String[] getUserData(String username) {
        ArrayList<String[]> userData = Helper.readData(userDataFile);
        for (String[] userDatum : userData) {
            if (userDatum[1].equalsIgnoreCase(username)) {
                return userDatum;
            }
        }
        return new String[0];
    }

    /**
     * This method validate the username
     * @return boolean
     */
    public boolean usernameValidate(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method validate password
     * @return boolean
     */
    public boolean passwordValidate(String password) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (password.equalsIgnoreCase(line[1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method hash the user password
     * @return String
     */
    public String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

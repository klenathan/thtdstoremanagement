package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountController {
    private final String userDataFile = "data/user.csv";

    private ArrayList<String[]> dataArr;

    private Account account = null;

    public Account getAccount() {
        return account;
    }

    public AccountController() {
        this.dataArr = Helper.readData(userDataFile);
    }

    /**
     * This method take the customer who is logged in the application
     */
    public void setCurrentAccount(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                this.account = new Account(line[0], line[1], line[3], line[4], line[5], line[6]);
            }
        }
    }

    /**
     * This is the method help user login to our application
     */
    public boolean login(String username, String password) {
        String generate = hashPassword(password);
        return (usernameValidate(username) && passwordValidate(generate));
    }

    /**
     * This method help user sign up our application
     */
    public void signup(String fullName, String username, String password, String phone) {
        String generatePass = hashPassword(password);
        String role = "user";
        long totalPayment = 0;
        String dataAdd = username + "," + generatePass + "," + fullName + "," + phone + "," + "none" + "," + role + "," + totalPayment;
        Helper.addData(userDataFile, dataAdd);
        this.dataArr = Helper.readData(userDataFile);
    }

    /**
     * This method validate the username
     * return true when username exist
     *
     * @return boolean
     */
    public boolean usernameValidate(String username) {
        for (int i = 1; i < this.dataArr.size(); i++) {
            String[] line = this.dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method validate the phone number 10 digits integer
     *
     * @return boolean
     */
    public boolean phoneValidate(String phone) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher m = pattern.matcher(phone);
        return m.matches();
    }

    /**
     * This method validate password
     *
     * @return boolean
     */
    public boolean passwordValidate(String password) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (password.equalsIgnoreCase(line[2])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method hash the user password
     *
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

/*
Admin feature
 */

    /**
     * This method set the Role for account
     */
    public String setRole(String uID, String role) {
        String message = "CHANGE ROLE SUCCESS";
        if (Helper.getAllId(userDataFile).contains(uID)) {
            if (uID.equals("U0")) {
                message = "THIS USER CAN NOT CHANGE THE ROLE\t";
            } else {
                switch (role) {
                    case "admin" -> Helper.modifyField(userDataFile, uID, 6, "admin");
                    case "user" -> Helper.modifyField(userDataFile, uID, 6, "user");
                }
            }
        } else {
            message = "THIS USER IS NOT EXIST";
        }
        return message;
    }

    /**
     * This method list all the user information
     */
    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }

    /**
     * This method see the customer information
     *
     * @return String
     */
    public String userViewInformation(String username) {
        String message = null;
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                String uname = line[1];
                String fName = line[3];
                String phone = line[4];
                String member = line[5];
                String totalPay = line[7];
                String RED = "\u001B[31m";
                String GREEN = "\u001B[32m";
                String RESET = "\u001B[0m";
                message = "================================\n" + RED + "\t\t" + uname + "'s information\n" + RESET
                        + "Username: " + GREEN + uname + RESET + "\n"
                        + "Full name: " + GREEN + fName + RESET + "\n"
                        + "Phone: " + GREEN + phone + RESET + "\n"
                        + "Membership: " + GREEN + member + RESET + "\n"
                        + "Total Payment: " + GREEN + totalPay + RESET + "\n"
                        + "================================";
            }
        }
        return message;
    }
}

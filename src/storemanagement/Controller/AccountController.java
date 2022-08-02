package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountController {
    private String userDataFile = "data/user.csv";
    private String order = "data/order.csv";

    private ArrayList<String[]> dataArr;

    private Account account = null;

    public Account getAccount() {
        return account;
    }

    public AccountController() {
        this.dataArr = Helper.readData(userDataFile);
    }


    /**
     * This method is to set current account
     * @param username: type String
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
     * This method helps user login to our application
     * @param username: type String
     * @param password: type String
     * @return true/false when the username and password is correct or not: type boolean
     */
    public boolean login(String username, String password) {
        String generate = hashPassword(password);
        return (usernameValidate(username) && passwordValidate(generate));
    }

    /**
     * This method help user sign up our application
     * @param fullName: type String
     * @param username: type String
     * @param password: type String
     * @param phone: type String
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
     * @param username: type String
     * @return true/false if the username exists or not: type boolean
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
     * This method validates whether the phone number contains 10-digit number
     * @param phone: type String
     * @return true/false if the phone number contains 10-digit number or not: type boolean
     */
    public boolean phoneValidate(String phone) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher m = pattern.matcher(phone);
        return m.matches();
    }

    /**
     * This method validates password
     * @param password: type String
     * @return true/false if the password is correct or not: type boolean
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
     * This method hashes the user password
     * @param password: type String
     * @return generatedPassword: type String
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

//    ADMIN FEATURE

    /**
     * This method is to set role for account (admin/user)
     * @param uID: type String
     * @param role: type String
     * @return message: type String
     */
    public String setRole(String uID, String role) {
        String message = "CHANGE ROLE SUCCESS";
        if (Helper.getAllId(userDataFile).contains(uID.toUpperCase())) {
            if (uID.equalsIgnoreCase("U1")){
                message = "THIS USER CAN NOT CHANGE THE ROLE\t";
            }else{
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
     * This method lists all user information
     * @return dataArr: type ArrayList<String[]>
     */
    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }


    /**
     * This method is to view user information
     * @param username: type String
     * @return message: type String
     */
    public String userViewInformation(String username) {
        String message = null;
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                String uname = line[1];
                String fname = line[3];
                String phone = line[4];
                String member = new OrderController().membershipCheck(line[0]);
                double totalBill = new OrderController().totalPayment(line[0]);
                message = "================================\n" + Helper.error("\t\t" + uname + "'s information\n")
                        + "Username: " + Helper.green(uname) + "\n"
                        + "Fullname: " + Helper.green(fname) + "\n"
                        + "Phone: " + Helper.green(phone) + "\n"
                        + "Membership: " + Helper.green(member) + "\n"
                        + "Total Payment: " + Helper.green(String.valueOf(totalBill)) + "\n"
                        + "================================";
            }
        }
        return message;
    }
}

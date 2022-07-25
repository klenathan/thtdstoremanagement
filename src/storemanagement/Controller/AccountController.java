package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AccountController {
    private String userDataFile = "data/user.csv";
    private String order = "data/order.csv";

    private ArrayList<String[]> dataArr;
    private ArrayList<String[]> orderArr;

    private Account account = null;

    public Account getAccount() {
        return account;
    }

    public AccountController() {
        this.dataArr = Helper.readData(userDataFile);
        this.orderArr = Helper.readData(order);

    }


    public void setCurrentAccount(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                this.account = new Account(line[0], line[1], line[3], line[4], line[5]);
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
    public boolean signup(String fullName, String username, String password, String phone) {
        String generatePass = hashPassword(password);
        String dataAdd = username + "," + generatePass + "," + fullName + "," + phone + "," + "none";
        if (usernameValidate(username) || username.contains(" ")) {
            return false;
        }
        Helper.addData(userDataFile, dataAdd);
        return true;
    }

    /**
     * This method get user data
     *
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
     * This medthod check the role of account
     *
     * @return boolean
     */
    public boolean checkRole(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                if (line[6].equalsIgnoreCase("admin")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method set the Role for account
     */
    public void setRole(String uID, String role) {
        if (Helper.getAllId(userDataFile).contains(uID)) {
            switch (role) {
                case "admin":
                    Helper.modifyField(userDataFile, uID, 6, "admin");
                case "user":
                    Helper.modifyField(userDataFile, uID, 6, "user");
            }
        }
    }

    /**
     * This method list all the user information
     */
    public void listAllUser() {
        Helper.listAll(userDataFile);
    }

    public void membership(String username) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                double payment = Double.parseDouble(line[7]);
                if (payment >= 5000000 && payment < 10000000) {
                    Helper.modifyField(userDataFile, line[0], 5, "Silver");
                } else if (payment >= 10000000 && payment < 25000000) {
                    Helper.modifyField(userDataFile, line[0], 5, "Gold");
                } else if (payment >= 25000000) {
                    Helper.modifyField(userDataFile, line[0], 5, "Platinum");
                } else {
                    Helper.modifyField(userDataFile, line[0], 5, null);
                }
            }

        }
    }

    public BigDecimal totalPayment(String userID) {
        double totalPayment = 0;

        for (int i = 1; i < orderArr.size(); i++) {
            String[] line = orderArr.get(i);
            if (userID.equalsIgnoreCase(line[2]) && line[5].equalsIgnoreCase("PAID")) {
                double orderBill = Double.parseDouble(line[4]);
                totalPayment += orderBill;
            }
        }
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line1 = dataArr.get(i);
            if (userID.equalsIgnoreCase(line1[0])) {
                double payment = Double.parseDouble(line1[7]);
                totalPayment += payment;
            }
        }
        return BigDecimal.valueOf(totalPayment);
    }
    
    public void addTotalPayment(String userID) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (userID.equalsIgnoreCase(line[0])) {
                BigDecimal value = totalPayment(userID);
                Helper.modifyField(userDataFile, line[0], 7, value.toPlainString());
            }

        }
    }
 }

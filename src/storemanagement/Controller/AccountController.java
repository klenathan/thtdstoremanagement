package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;
import storemanagement.View.Menu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountController {
    String userDataFile = "data/user.csv";
    private Pattern pattern;
    private String USERNAME_PATTERN = "^/s$";
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
    public boolean login (String username, String password){
        return (usernameValidate(username) && passwordValidate(password));
    }

    /**
     * This method help user sign up our application
     */
    public boolean signup(String fullName, String username, String password, String phone) {
        String generatePass = hashPassword(password);
        String dataAdd = username + "," + generatePass + "," + fullName + "," + phone;
        if(usernameValidate(username)){
            return false;
        }else if (!usernameValidate(username)){
            if(!signupUsernameValidate(username)){
                return false;
            }
        }
        Helper.addData(userDataFile, dataAdd);
        return true;
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
     * This method do not allow user input the whitespace for password
     * @return boolean
     */
    public boolean signupUsernameValidate(String username){
        pattern = Pattern.compile(USERNAME_PATTERN);
        return pattern.matcher(username).matches();
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

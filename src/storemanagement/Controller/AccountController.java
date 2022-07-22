package storemanagement.Controller;

import storemanagement.Model.Account;
import storemanagement.Service.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AccountController {
    private String userDataFile = "data/user.csv";
    private Pattern pattern;
    private String USERNAME_PATTERN = "^/s$";
<<<<<<< Updated upstream
    Scanner input = new Scanner(System.in);;
    ArrayList<String[]> dataArr = Helper.readData(userDataFile);
    Account account;
=======
    private ArrayList<String[]> dataArr;
    private Account account;
>>>>>>> Stashed changes

    public static void main(String[] args) {
        AccountController acc = new AccountController();
    }

    public Account getAccount() {
        return account;
    }

    public AccountController() {
<<<<<<< Updated upstream
        System.out.println("init Account");
=======
        this.dataArr = Helper.readData(userDataFile);
    }

    public void setCurrentAccount(String username) {
        System.out.println("here");
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (username.equalsIgnoreCase(line[1])) {
                this.account = new Account(line[0], line[1], line[3], line[4], line[5]);
            }
        }
>>>>>>> Stashed changes
    }

    /**
     * This is the method help user login to our application
     */
    public boolean login(String username, String password) {
        System.out.println("Login init");
//        return (usernameValidate(username) && passwordValidate(password));
        return true;
    }

    /**
     * This method help user sign up our application
     */
    public boolean signup(String fullName, String username, String password, String phone) {
        String generatePass = hashPassword(password);
        String dataAdd = username + "," + generatePass + "," + fullName + "," + phone + "," + "none";
        if (usernameValidate(username)) {
            return false;
        } else if (!usernameValidate(username)) {
            if (!signupUsernameValidate(username)) {
                return false;
            }
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
     * This method do not allow user input the whitespace for password
     * 
     * @return boolean
     */
    public boolean signupUsernameValidate(String username) {
        pattern = Pattern.compile(USERNAME_PATTERN);
        return pattern.matcher(username).matches();
    }

    /**
     * This method validate password
     * 
     * @return boolean
     */
    // TODO: 22/07/2022 Password validate nhung ma chua hash password?? 
    public boolean passwordValidate(String password) {
        for (int i = 1; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            if (password.equalsIgnoreCase(line[1])) {
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
}

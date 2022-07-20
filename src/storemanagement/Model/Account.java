package storemanagement.Model;

import storemanagement.Service.Helper;

public class Account {
//    private String cusID;
    private final String username;
    private final String password;
    private final String fullName;
    public String membership;
    int phone;
    public Account(String username, String password, String fullName, int phone, String membership){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.membership = membership;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMembership() {
        return membership;
    }

    public int getPhone() {
        return phone;
    }
}

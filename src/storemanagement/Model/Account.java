package storemanagement.Model;

import storemanagement.Service.Helper;

public class Account {
//    private String cusID;
    private String username;
    private String password;
    private String fullName;
    public String membership;
    public String phone;
    public Account(String username, String password, String fullName, String phone, String membership){
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

    public String getPhone() {
        return phone;
    }
}

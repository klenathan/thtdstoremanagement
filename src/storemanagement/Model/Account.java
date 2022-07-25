package storemanagement.Model;

import storemanagement.Service.Helper;

public class Account {
//    private String cusID;
    private String userId;
    private String username;
    private String fullName;
    public String phone;
    public String membership;


    public Account(String userId, String username, String fullName, String phone, String membership){
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.membership = membership;
    }

    public String getUsername() {
        return username;
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

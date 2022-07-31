package storemanagement.Model;

public class Account {
//    private String cusID;
    private String userId;
    private String username;
    private String fullName;
    public String phone;
    public String membership;
    private String role;


    public Account(String userId, String username, String fullName, String phone, String membership, String role){
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.membership = membership;
        this.role = role;
    }

    public String getUserId() {
        return userId;
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
    public String getRole(){return role;}
}

package storemanagement.Controller;

import storemanagement.Model.Order;
import storemanagement.Service.Helper;

import java.util.ArrayList;

public class OrderController {

    private final String orderDataFile = "data/order.csv";
    private final String userDataFile = "data/user.csv";

    private ArrayList<String[]> dataArr;

    public OrderController() {
        this.dataArr = Helper.readData(orderDataFile);
    }

    public static void main(String[] args) {
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }

    public void createOrder(String productId, String userId, int quantity, long price) {
        double totalBill = (quantity * price) * (1 - membershipDiscount(userId));
//        System.out.println("check " + membershipDiscount(userId));

        String orderDetail = productId
                + "," + userId + "," + quantity + "," + totalBill + "," + "UNPAID";
        Helper.addData(orderDataFile, orderDetail);
        this.dataArr = Helper.readData(orderDataFile);
    }

    public void updateOrderStatus(String orderId) {
        // String tempData;
        if (Helper.getAllId(orderDataFile).contains(orderId)) {
            Helper.modifyField(orderDataFile, orderId, 5, "PAID");
            this.dataArr = Helper.readData(orderDataFile);
        } else {
            System.out.println("The product ID \"" + orderId + "\" does not exist in the store!");
        }
    }

    public String membershipCheck(String customerID) {
        double totalBill = 0;
        String membership = "";
        for (String[] line : dataArr) {
            if (customerID.equalsIgnoreCase(line[2]) && line[5].equalsIgnoreCase("paid")) {
                totalBill += Double.parseDouble(line[4]);
            }
        }
        if (totalBill > 25000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "Platinum");
            membership = "platinum";
        } else if (totalBill > 10000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "Gold");
            membership = "gold";
        } else if (totalBill > 5000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "Silver");
            membership = "silver";
        } else {
            membership = "none";
        }
        System.out.println(membership);
        return membership;
    }

    public double membershipDiscount(String customerID) {
        String membership = membershipCheck(customerID);
        double discount = 0;
        if (membership.equalsIgnoreCase("silver")) {
            discount = 0.05;
        } else if (membership.equalsIgnoreCase("gold")) {
            discount = 0.1;
        } else if (membership.equalsIgnoreCase("platinum")) {
            discount = 0.15;
        } else {
            discount = 0;
        }
        return discount;
    }

    public ArrayList<String[]> getCurrenUserOrders(String userId) {
        ArrayList<String[]> res = new ArrayList<>();
        String orderUserId;
        for (int i = 0; i < this.dataArr.size(); i++) {
            orderUserId = this.dataArr.get(i)[2];
            if (orderUserId.equalsIgnoreCase(userId)) {
                res.add(this.dataArr.get(i));
            }
        }
        return res;
    }

    public String[] getOrderInfo(String orderId) {
        return Helper.getDataFromLine(orderDataFile, orderId);
    }

    public void listAllOrder() {
        Helper.listAll(orderDataFile);
    }
}

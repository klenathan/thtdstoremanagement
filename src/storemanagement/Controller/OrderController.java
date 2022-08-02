package storemanagement.Controller;

import storemanagement.Service.Helper;

import java.util.ArrayList;

public class OrderController {

    private final String orderDataFile = "data/order.csv";
    private final String userDataFile = "data/user.csv";

    private ArrayList<String[]> dataArr;

    public OrderController() {
        this.dataArr = Helper.readData(orderDataFile);
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }

    public int quantityValidate(int quantity) {
        if (quantity < 0) {
            return 0;
        } else {
            return quantity;
        }
    }

    public void createOrder(String productId, String userId, int quantity, long price) {
        int finalQuantity = quantityValidate(quantity);
        long totalBill = (long) ((finalQuantity * price) * (1 - membershipDiscount(userId)));
        String orderDetail = productId
                + "," + userId + "," + finalQuantity + "," + totalBill + "," + "UNPAID";
        Helper.addData(orderDataFile, orderDetail);
        this.dataArr = Helper.readData(orderDataFile);
    }

    public void updateOrderStatus(String orderId) {
        if (Helper.getAllId(orderDataFile).contains(orderId)) {
            Helper.modifyField(orderDataFile, orderId, 5, "PAID");
            this.dataArr = Helper.readData(orderDataFile);
            String userID;
            String[] line = Helper.getDataFromLine(orderDataFile, orderId);
            userID = (line[2]);
            membershipCheck(userID);
        } else {
            System.out.println("The product ID \"" + orderId + "\" does not exist!");
        }
    }

    public double totalPayment(String customerID) {
        double totalBill = 0;

        for (String[] line : dataArr) {
            if (customerID.equalsIgnoreCase(line[2]) && line[5].equalsIgnoreCase("paid")) {
                totalBill += Double.parseDouble(line[4]);
            }
        }

        Helper.modifyField(userDataFile, customerID, 7, Double.toString(totalBill));
        return totalBill;
    }

    public String membershipCheck(String customerID) {
        double totalBill = totalPayment(customerID);
        String membership;
        if (totalBill > 25000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "platinum");
            membership = "platinum";
        } else if (totalBill > 10000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "gold");
            membership = "gold";
        } else if (totalBill > 5000000.0) {
            Helper.modifyField(userDataFile, customerID, 5, "silver");
            membership = "silver";
        } else {
            membership = "none";
        }

        return membership;
    }

    public double membershipDiscount(String customerID) {
        String membership = membershipCheck(customerID);
        double discount;
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

    public String[] getOrderInfo(String orderId, String userID) {
        String[] arr = Helper.getDataFromLine(orderDataFile, orderId.toUpperCase());
        if (!userID.equalsIgnoreCase(arr[2])) {
            arr = new String[0];
        }
        return arr;
    }

}

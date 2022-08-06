package storemanagement.Controller;

import storemanagement.Service.Helper;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OrderController {

    private final String orderDataFile = "data/order.csv";
    private final String userDataFile = "data/user.csv";
    private ArrayList<String[]> dataArr;

    public OrderController() {
        this.dataArr = Helper.readData(orderDataFile);
    }

    /**
     * This method validate whether the quantity input is >= 0 or not
     *
     * @param quantity: type int
     * @return 0 if quantity < 0, otherwise return quantity: type int
     */
    public int quantityValidate(int quantity) {
        return Math.max(0, quantity);
    }

    /**
     * This method is to create order
     *
     * @param productId: type String
     * @param userId:    type String
     * @param quantity:  type int
     * @param price:     type long
     */
    public void createOrder(String productId, String userId, int quantity, long price) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        int finalQuantity = quantityValidate(quantity);
        long totalBill = (long) ((finalQuantity * price) * (1 - membershipDiscount(userId)));
        String orderDetail = productId
                + "," + userId + "," + finalQuantity + "," + totalBill + "," + "UNPAID" + "," + dtf.format(now);
        Helper.addData(orderDataFile, orderDetail);
        this.dataArr = Helper.readData(orderDataFile);
    }


    /**
     * This method is to update other status from UNPAID to PAID
     *
     * @param orderId: type String
     */
    public String updateOrderStatus(String orderId) {
        String message;
        if (Helper.getAllId(orderDataFile).contains(orderId)) {
            Helper.modifyField(orderDataFile, orderId, 5, "PAID");
            this.dataArr = Helper.readData(orderDataFile);
            String userID;
            String[] line = Helper.getDataFromLine(orderDataFile, orderId);
            userID = (line[2]);
            membershipCheck(userID);
            Helper.modifyField(userDataFile, userID, 7, Long.toString(totalPayment(userID)));
            message = Helper.green("Successfully updated!");
        } else {
            message = Helper.error("The product ID \"" + orderId + "\" does not exist!");
        }
        return message;
    }

    /**
     * This method is to get the total payment of a customer based on customer id
     *
     * @param customerID: type String
     * @return totalBill: type double
     */
    public long totalPayment(String customerID) {
        long totalBill = 0;

        for (String[] line : dataArr) {
            if (customerID.equalsIgnoreCase(line[2]) && line[5].equalsIgnoreCase("paid")) {
                totalBill += Double.parseDouble(line[4]);
            }
        }
        return totalBill;
    }

    /**
     * This method is to update the membership of a customer based on their total bill
     *
     * @param customerID: type String
     * @return membership: type String
     */
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

    /**
     * This method checks the discount of a user based on their membership
     *
     * @param customerID: type String
     * @return discount: type double
     */
    public double membershipDiscount(String customerID) {
        String membership = membershipCheck(customerID);
        double discount = 0;
        if (membership.equalsIgnoreCase("silver")) {
            discount = 0.05;
        } else if (membership.equalsIgnoreCase("gold")) {
            discount = 0.1;
        } else if (membership.equalsIgnoreCase("platinum")) {
            discount = 0.15;
        }
        return discount;
    }

    /**
     * This method is to get current user order
     *
     * @param userId: type String
     * @return res: type ArrayList<String[]>
     */
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


    /**
     * This method is to get order information of a user
     *
     * @param orderId: type string
     * @param userID:  type string
     * @return arr: type String[]
     */
    public String[] getOrderInfo(String orderId, String userID) {
        String[] arr = Helper.getDataFromLine(orderDataFile, orderId.toUpperCase());
        if (!userID.equalsIgnoreCase(arr[2])) {
            arr = new String[0];
        }
        return arr;
    }

    /**
     * This method return the order at the end of a day & its total bill
     * @return res: type ArrayList<String[]>
     */
    public ArrayList<String[]> dayOrder(){
        long totalBill = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        ArrayList<String[]> res = new ArrayList<>();
        for(String[] line : dataArr){
            if (currentTime.equalsIgnoreCase(line[6]) && line[5].equalsIgnoreCase("PAID")){
                String id = line[0];
                totalBill += Long.parseLong(line[4]);
                res.add(Helper.getDataFromLine(orderDataFile,id));
            }
        }
        String total = String.format("%,d", totalBill);
        String[] arr = {"Total bill", "", "", "", total, "", currentTime};
        res.add(arr);
        return res;
    }

}
package storemanagement.Controller;

import com.sun.tools.javac.Main;
import storemanagement.Model.Order;
import storemanagement.Service.Helper;

import java.util.ArrayList;

public class OrderController {

    public static void main(String[] args) {
        OrderController orderController = new OrderController();
        orderController.updateOrderStatus("O1");
    }
    private final String orderDataFile = "data/order.csv";
    private ArrayList<String[]> dataArr;

    public OrderController() {
        this.dataArr = Helper.readData(orderDataFile);
    }

    public ArrayList<String[]> getDataArr() {
        return dataArr;
    }

    public void createOrder(String productId, String userId, int quantity, long price){
        long totalBill = quantity * price;

        String orderDetail = productId
                + "," + userId + "," + quantity + "," + totalBill + "," + "UNPAID";
        Helper.addData(orderDataFile, orderDetail);
    }

    public void updateOrderStatus(String orderId) {
//        String tempData;
        if(Helper.getAllId(orderDataFile).contains(orderId)) {
            Helper.modifyField(orderDataFile, orderId,5, "PAID");
        } else {
            System.out.println("The product ID \"" + orderId + "\" does not exists in the store!");
        }
    }

    public String membershipCheck(String customerID) {
        long totalBill = 0;
        String membership;
        for (String[] line: dataArr) {
            if (customerID.equalsIgnoreCase(line[2]) && line[5].equalsIgnoreCase("paid")) {
                totalBill += Integer. parseInt(line[4]);
            }
        }

        if (totalBill > 25000000) {
            membership = "Platinum";
        } else if (totalBill > 10000000) {
            membership = "Gold";
        } else if (totalBill > 5000000) {
            membership = "Silver";
        } else {
            membership = "None";
        }
        return membership;
    }


    public double membershipDiscount(String customerID) {
        String membership = membershipCheck(customerID);
        double discount;
        if (membership.equalsIgnoreCase("Silver")) {
            discount = 0.05;
        } else if (membership.equalsIgnoreCase("Gold")) {
            discount = 0.1;
        } else if (membership.equalsIgnoreCase("Platinum")) {
            discount = 0.15;
        } else {
            discount = 0;
        }
        return discount;
    }

        
    public ArrayList<String[]> getCurrenUserOrders(String userId) {
        ArrayList<String[]> res = new ArrayList<>();
        for (int i = 0; i < this.dataArr.size(); i++) {
            String orderUserId = this.dataArr.get(i)[2];
            System.out.println(userId + " orderUserId: " + orderUserId +" "+ orderUserId.equalsIgnoreCase(userId));
            // TODO: 22/07/2022 : This check always return false :(( SOS 
            if (orderUserId.equalsIgnoreCase(userId)) {

                res.add(this.dataArr.get(i));
            }
        }
        return res;
    }


    public void listAllOrder() {
        Helper.listAll(orderDataFile);
    }
}

package storemanagement.Controller;

import storemanagement.Model.Order;
import storemanagement.Service.Helper;

import java.util.ArrayList;
import java.util.Objects;

public class OrderController {

    private final String orderDataFile = "data/order.csv";
    ArrayList<String[]> dataArr;

    public OrderController() {
        dataArr = Helper.readData(orderDataFile);
    }

    public void createOrder(String productId, String userId, int quantity){

        String orderId = "a";
        long totalBill = quantity * 100000L;

        String orderDetail = orderId + "," + productId
                + "," + userId + "," + quantity + "," + totalBill + "," + "UNPAID";
        Helper.addData(this.orderDataFile, orderDetail);
    }

    public void updateOrderStatus(String orderId) {
//        String tempData;
        if(Helper.getAllId(orderDataFile).contains(orderId)) {
            for(String[] line: dataArr){
                if(Objects.equals(line[0], orderId)){
                    String newData = line[0] + ',' + line[1] + "," + line[2]
                            + "," + line[3] + ',' + line[4] + "," + "PAID";
                    Helper.deleteLine(orderDataFile, orderId);
                    Helper.addData(orderDataFile, newData);
                }
            }
        }
    }

}

import storemanagement.Service.Helper;

import java.util.ArrayList;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
//        testFileOpen();
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }

    public static void testWrite() {
        String userFile = "./data/user.csv";
        String productFile = "./data/product.csv";
        String content = "";

        ArrayList<String[]> dataArr = Helper.readData(productFile);

        for (String[] line : dataArr) {
            for (String s : line) {
                content += s + ", ";
            }
            content += "\n";
        }
        Helper.addData("testFile.csv", content);
    }
}

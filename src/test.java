import java.util.ArrayList;
import java.util.HashMap;

public class test {
    public static void main(String[] args) {
//        testFileOpen();
        testWrite();
        String userFile = "./data/user.csv";
    }

    public static void testWrite() {
        String userFile = "./data/user.csv";
        String productFile = "./data/product.csv";
        String content = "";

        ArrayList<String[]> dataArr = Helper.readData(productFile);

        for (int i = 0; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            for (int j = 0; j < line.length; j++) {
                content += line[j] + ", ";
            }
            content += "\n";
        }
        Helper.addData("testFile.txt", content);
    }
}

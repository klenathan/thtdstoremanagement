import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        System.out.println("here");
        String userFile = "../data/user.csv";
        ArrayList<String[]> dataArr = Helper.readData(userFile);
        for (int i = 0; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            System.out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3]);
        }
    }
}

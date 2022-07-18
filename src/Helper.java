import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper {
    public static void main(String[] args) {

    }

    public static ArrayList<String[]> readData(String file) {
        ArrayList<String[]> dataArr = new ArrayList<>();
        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);
            String[] lineArray;

             while (reader.hasNext()) {
                 String line = reader.nextLine();
                 lineArray = line.split(",", -1);
                 dataArr.add(lineArray);
             }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return dataArr;
    }
}

package storemanagement.Service;

import java.io.*;
import java.util.*;

public class Helper {



    public static void main(String[] args) {
        deleteLine("testFile.csv", "p1");
    }

    public static ArrayList<String[]> readData(String file) {
        ArrayList<String[]> dataArr = new ArrayList<>();
        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);

             while (reader.hasNext()) {
                 String line = reader.nextLine();
                 String[] lineArray = line.split(",", -1);
                 dataArr.add(lineArray);
             }
             reader.close();
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return dataArr;
    }

    public static ArrayList<String> getAllId(String file) {
        ArrayList<String> idArr = new ArrayList<>();

        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);

            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] lineArray = line.split(",", -1);
                idArr.add(lineArray[0]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return idArr;
    }

    public static void addData(String file, String newData){
        try {
            File inputFile = new File(file);
            String res = "";
            Scanner reader = new Scanner(inputFile);
            // Read current data to String res
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] lineArray = line.split(",", -1);

                for (int i = 0; i < lineArray.length - 1; i++) {
                    res += lineArray[i] + ",";
                }
                res += lineArray[lineArray.length - 1];
                res += "\n";
            }
            // Write new data to file combine with new line
            PrintWriter fileWr = new PrintWriter(inputFile);
            res += newData;
            fileWr.print(res);
            // close stream
            fileWr.flush();
            fileWr.close();
        } catch (IOException e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }

    public static void deleteLine(String file, String id) {
        if (getAllId(file).contains(id)) {
            try {
                File inputFile = new File(file);
                String res = "";
                Scanner reader = new Scanner(inputFile);
                // Read current data to String res
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    String[] lineArray = line.split(",", -1);
                    // Only add non-equal id line
                    if (!Objects.equals(lineArray[0], id)) {
                        for (int i = 0; i < lineArray.length - 1; i++) {
                            res += lineArray[i] + ",";
                        }
                        res += lineArray[lineArray.length - 1];
                        res += "\n";
                    }
                }
                // Write new data to file combine with new line
                FileWriter fileWr = new FileWriter(file);
                fileWr.write(res);
                // close stream
                fileWr.flush();
                fileWr.close();
            } catch (IOException e) {
                System.out.println(e);
                e.getStackTrace();
            }
        } else {
            System.out.println("ID does not exist");
        }
    }

}

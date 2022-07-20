package storemanagement.Service;

import java.io.*;
import java.util.*;

public class Helper {

    public static void main(String[] args) {
//        addData("data/product.csv", "product2,test,100000");
        modifyField("testFile.csv", "321e7f1f-0e96-4fda-9649-da506aac432d", 3, "600");
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

    public static String randomID() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    public static void addData(String file, String newData) {
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
            String finalData = randomID() + "," + newData;
            res += finalData;
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

    public static int getLatestId(String file) {
        ArrayList<String> idArray = getAllId(file);
        String latestStringId = idArray.get(idArray.size() - 1).substring(1);
        try {
            return Integer.parseInt(latestStringId);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void modifyField(String file, String id, int index, String newValue) {
        StringBuilder tempRes = new StringBuilder();
        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);

//            productId,productName,category,price
//            c35bcadd-ea80-40ba-8b87-606cbcaa5bc5,product2,test,100000
//            48abfb44-cab8-4d75-a457-7202d1ea97e3,product1,test,0
//            6c076bd4-11d1-47b2-b331-c6d0e865a7ae,product4,test,124000
//            321e7f4f-0e96-4fda-9649-da506aac432d,product3,test,123000
//            6c076bd2-11d1-47b2-b331-c6d0e865a7ae,product4,test,124000
//            321e7f1f-0e96-4fda-9649-da506aac432d,product3,test,123000

            while(reader.hasNext()) {
                String lineStr = reader.nextLine();
//                String newLine = null;
                if (lineStr.split(",")[0] == id) {
                    System.out.println("Match");
                    String newLine = "";
                    String[] line = lineStr.split(",");
                    line[index] = newValue;
                    for (int i = 0; i < line.length - 1; i++) {
                        newLine += line[i] + ",";
                    }
                    newLine += "\n";
                    tempRes.append(newLine);
                } else {
                    tempRes.append(lineStr).append("\n");
                }

                FileWriter fileWr = new FileWriter(file);
                System.out.println(lineStr);
                fileWr.write(lineStr);
                // close stream
                fileWr.flush();
                fileWr.close();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

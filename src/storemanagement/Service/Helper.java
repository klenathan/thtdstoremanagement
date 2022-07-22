package storemanagement.Service;

import java.io.*;
import java.util.*;

public class Helper {

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

    public static String generatedID(String file) {
        int latestID = getLatestId(file) + 1;
        String firstChar = "";
        if (file.equalsIgnoreCase("data/order.csv")) {
            firstChar = "O";
        } else if (file.equalsIgnoreCase(("data/product.csv"))) {
            firstChar = "P";
        } else if (file.equalsIgnoreCase("data/user.csv")) {
            firstChar = "U";
        }
        return firstChar + latestID;
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
            String newId = generatedID(file);
            PrintWriter fileWr = new PrintWriter(inputFile);

            String finalData = newId + "," + newData;
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

    public static String[] getDataFromLine(String file, String id) {
        if (getAllId(file).contains(id)) {
            try {
                File inputFile = new File(file);
                String res = "";
                Scanner reader = new Scanner(inputFile);
                // Read current data to String res
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    return line.split(",", -1);
                }
            } catch (IOException e) {
                System.out.println(e);
                e.getStackTrace();
            }
        } else {
            System.out.println("ID does not exist");
            return null;
        }
        return null;
    }

    // DONE
    public static void modifyField(String file, String id, int index, String newValue) {
        StringBuilder tempRes = new StringBuilder();
        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);

            while(reader.hasNext()) {
                String lineStr = reader.nextLine();
                if (lineStr.split(",")[0].equalsIgnoreCase(id)) {
                    String newLine = "";
                    String[] line = lineStr.split(",");
                    line[index] = newValue;
                    for (int i = 0; i < line.length; i++) {
                        newLine += line[i] + ",";
                    }
                    newLine = newLine.substring(0, newLine.length() - 1);
                    newLine +=  "\n";
                    tempRes.append(newLine);
                } else {
                    tempRes.append(lineStr).append("\n");
                }

                FileWriter fileWr = new FileWriter(file);
                fileWr.write(String.valueOf(tempRes));
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

    public static void listAll(String datafile) {
        ArrayList<String[]> dataArr = Helper.readData(datafile);
        for (int i = 0; i < dataArr.size(); i++) {
            String[] line = dataArr.get(i);
            for (String j: line) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

}

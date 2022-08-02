package storemanagement.Service;

import java.io.*;
import java.util.*;

public class Helper {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    /**
     * This method is to read data from a file
     * @param file: type string
     * @return dataArr: type ArrayList<String[]>
     */
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

    /**
     * This method is to get all primary key (id) from a file
     * @param file: type string
     * @return idArr: type ArrayList<String>
     */
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

    /**
     * This method is to get the latest id in a file
     * @param file: type string
     * @return the latest id if it exists, otherwise return 0: type int
     */
    public static int getLatestId(String file) {
        ArrayList<String> idArray = getAllId(file);
        String latestStringId = idArray.get(idArray.size() - 1).substring(1);
        try {
            return Integer.parseInt(latestStringId);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * This method is to generate id in a file
     * @param file: type string
     * @return firstChar + latestID (the final id): type String
     */
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

    /**
     * This method is to add new data to a file
     * @param file: type string
     * @param newData: type string
     */
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

    /**
     * This method is to get data from line in a file based on the primary key (id)
     * @param file: type string
     * @param id: type string
     * @return lineArr if the id exists, otherwise return String[0]: type String[]
     */
    public static String[] getDataFromLine(String file, String id) {
        if (getAllId(file).contains(id)) {
            try {
                File inputFile = new File(file);
                Scanner reader = new Scanner(inputFile);
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    String[] lineArr = line.split(",", -1);

                    if (lineArr[0].equalsIgnoreCase(id)) {
                        return lineArr;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
                e.getStackTrace();
            }
        } else {
            System.out.println("ID does not exist");
            return new String[0];
        }
        return new String[0];
    }

    /**
     * This method is to modify field of an data line in a file based on its id
     * @param file: type string
     * @param id: type string
     * @param index: type string
     * @param newValue: type string
     */
    public static void modifyField(String file, String id, int index, String newValue) {
        StringBuilder tempRes = new StringBuilder();
        try {
            File inputFile = new File(file);
            Scanner reader = new Scanner(inputFile);

            while (reader.hasNext()) {
                String lineStr = reader.nextLine();
                if (lineStr.split(",")[0].equalsIgnoreCase(id)) {
                    String newLine = "";
                    String[] line = lineStr.split(",");
                    line[index] = newValue;
                    for (int i = 0; i < line.length; i++) {
                        newLine += line[i] + ",";
                    }
                    newLine = newLine.substring(0, newLine.length() - 1);
                    newLine += "\n";
                    tempRes.append(newLine);
                } else {
                    tempRes.append(lineStr).append("\n");
                }
            }
            FileWriter fileWr = new FileWriter(file);
            fileWr.write(String.valueOf(tempRes));
            // close stream
            fileWr.flush();
            fileWr.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method generates red message
     * @param mes: type string
     * @return RED + mes + RESET (red text): type string
     */
    public static String error(String mes) {
        return RED + mes + RESET;
    }

    /**
     * This method generates green message
     * @param mes: type string
     * @return GREEN + mes + RESET (green text): type string
     */
    public static String green(String mes) {
        return GREEN + mes + RESET;
    }
}
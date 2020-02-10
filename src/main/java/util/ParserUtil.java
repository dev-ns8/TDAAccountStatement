package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class ParserUtil {

    private static final Pattern DESCRIPTION_SYMBOL = Pattern.compile("\\s");

    public static boolean isEmpty(String[] line) {
        for (String cell : line) {
            if (cell.equals("") || cell.length() == 0) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static LocalDateTime stringToDateTime(String cell) throws Exception {
        String[] dateAndTime = Pattern.compile("\\W").split(cell);
        if (dateAndTime.length != 5) {
            throw new Exception("stringToDateTime() :: String representation of date is malformed!");
        }
        return LocalDateTime.of(Integer.parseInt(dateAndTime[2]), Integer.parseInt(dateAndTime[0]), Integer.parseInt(dateAndTime[1]), Integer.parseInt(dateAndTime[3]), Integer.parseInt(dateAndTime[4]));
    }

    public static LocalDate stringToDate(String cell) {
        String[] parts = cell.split("/");
        if (parts.length != 3) {
            System.out.println(cell);
            System.out.println("Wrong date format from Excel cell");
            return null;
        }
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static String symbolFromDescription(String description) {
        String[] parts = DESCRIPTION_SYMBOL.split(description);
        return parts[2];
    }

    public static double qtyFromDescription(String description) {
        String[] parts = DESCRIPTION_SYMBOL.split(description);
        return Double.parseDouble(parts[1]);
    }

    public static LocalTime stringToTime(String cell) {
        String[] parts = cell.split(":");
        if (parts.length != 3) {
            System.out.println("Wrong time format from Excel cell");
            return null;
        }
        String hour = parts[0];
        String minute = parts[1];
        String second = parts[2];
        return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
    }

    public static String stripSpecialChars(String string) {
        String t;
        if (string.contains("(")) {
            t = string.substring(2, string.length()-1);
        } else if (string.contains("%")) {
            t = string.substring(0, string.length()-1);
        } else {
            t = string.substring(1);
        }
        if (t.indexOf("\"") > -1) {
            char[] s = string.toCharArray();
            StringBuilder r = new StringBuilder();
            for (char c : s) {
                if (c == '\"') {
                    continue;
                }
                r.append(c);
            }
            return r.toString().replaceAll(",", "");
        }
        return t;
    }

    public static boolean checkForStartOfSection(String[] row, String section) {
        return row[0].equals(section) ? true : false;
    }

    public static List<String[]> getRawData(List<String[]> lines) {
        List<String[]> rawReturnList = new LinkedList<>();
        for (String[] line : lines) {
            if (!isEmpty(line)) {
                rawReturnList.add(line);
            } else {
                break;
            }
        }
        return rawReturnList;
    }
}

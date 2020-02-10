package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileUtility {

    private static final Pattern SPLIT_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    public static List<String[]> readFile(File file) throws IOException {
        List<String[]> out = new LinkedList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            String row;
            while ((row = input.readLine()) != null) {
                out.add(SPLIT_PATTERN.split(removeUtf8Bom(row), -1));
            }
        }
        return out;
    }

    private static String removeUtf8Bom(String s) {
        String utfBom = "\uFEFF";
        return s.startsWith(utfBom) ? s.substring(1) : s;
    }
}

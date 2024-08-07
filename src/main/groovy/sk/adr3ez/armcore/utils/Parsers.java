package sk.adr3ez.armcore.utils;

public class Parsers {

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String string) {
        return Boolean.parseBoolean(string);
    }

}

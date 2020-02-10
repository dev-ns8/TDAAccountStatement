package model.enums;

public enum InstrumentType {
    ETF,
    STOCK,
    OPTION;

    public static InstrumentType fromLabel(String label) {
        switch (label) {
            case "ETF":
                return ETF;
            case "STOCK":
                return STOCK;
            case "OPTION":
                return OPTION;
            default:
                System.out.println("Given instrument is not valid");
                return null;
        }
    }
}

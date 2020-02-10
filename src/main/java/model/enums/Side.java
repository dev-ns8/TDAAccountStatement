package model.enums;

public enum Side {
    BUY,
    SELL;

    public static Side fromLabel(String label) {
        switch (label) {
            case "SELL":
                return SELL;
            case "BUY":
                return BUY;
            default:
                System.out.println("Label given is not a valid transaction Side");
                return null;
        }
    }

    public static Side fromAmount(double amount) {
        if (amount < 0) {
            return BUY;
        } else if (amount > 0) {
            return SELL;
        } else {
            System.out.println("Side cannot be determined from a $0 amount");
            return null;
        }
    }
}

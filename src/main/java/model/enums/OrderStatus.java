package model.enums;

public enum OrderStatus {
    FILLED,
    CANCELED,
    WORKING;

    public static OrderStatus fromLabel(String label) {
        switch (label) {
            case "FILLED":
                return FILLED;
            case "CANCELLED":
                return CANCELED;
            case "WORKING":
                return WORKING;
            default:
                System.out.println("Not valid order status");
                return null;
        }
    }
}

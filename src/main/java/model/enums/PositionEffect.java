package model.enums;

public enum PositionEffect {
    TO_OPEN,
    TO_CLOSE;

    public static PositionEffect fromLabel(String label) {
        switch (label) {
            case "TO OPEN":
                return TO_OPEN;
            case "TO CLOSE":
                return TO_CLOSE;
            default:
                System.out.println("Not valid effect on position");
                return null;
        }
    }
}

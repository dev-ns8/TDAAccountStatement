package model.enums;

public enum CashBalanceCode {
        ACHR("ACHR"),   // ACH Deposit
        BAL("BAL"),    // Start of day cash-balance
        JRN("JRN"),    // Security transfer fee
        TRD("TRD"),    // Trade
        EFN("EFN"),    // Withdrawal
        ADJ("ADJ"),    // Courtesy credit
        DOI("DOI")     // Qualified Dividend
        ;

        String label;

        CashBalanceCode(String label) {
                this.label = label;
        }

        public static CashBalanceCode fromLabel(String label) {
                switch(label) {
                        case "ACHR":
                                return ACHR;
                        case "BAL":
                                return BAL;
                        case "JRN":
                                return JRN;
                        case "TRD":
                                return TRD;
                        case "EFN":
                                return EFN;
                        case "ADJ":
                                return ADJ;
                        case "DOI":
                                return DOI;
                        default:
                                System.out.println("Given label is not a qualified CashBalance Code :: " + label);
                                return null;
                }
        }


}

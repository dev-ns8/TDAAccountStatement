package util;

import model.entity.CashBalanceEntry;
import model.entity.EquityEntry;
import model.entity.PLEntry;
import model.entity.TradeHistoryEntry;
import model.enums.CashBalanceCode;
import model.enums.InstrumentType;
import model.enums.PositionEffect;
import model.enums.Side;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static util.ParserUtil.*;

public class AccountStatementUtil {

    private static final String PL_LABEL = "Profits and Losses";
    private static final String SUMMARY = "Account Summary";
    private static final String EQUITIES_LABEL = "Equities";
    private static final String TRADE_HISTORY_LABEL = "Account Trade History";
    private static final String CASH_BALANCE_LABEL = "Cash Balance";
    private static final Pattern SPLIT = Pattern.compile(",");

    private final File file;

    private static final String[] CASH_BALANCE = (
                    "DATE," +
                    "TIME," +
                    "TYPE," +
                    "REF #," +
                    "DESCRIPTION," +
                    "Misc Fees," +
                    "Commissions & Fees," +
                    "AMOUNT," +
                    "BALANCE"
            ).split(SPLIT.toString());

    private static final String[] EQUITIES = (
            "Symbol," +
                    "Description," +
                    "Qty," +
                    "Trade Price," +
                    "Mark," +
                    "Mark Value"
            ).split(SPLIT.toString());

    private static final String[] PL = (
            "Symbol," +
                    "Description," +
                    "P/L Open," +
                    "P/L %," +
                    "P/L Day," +
                    "P/L YTD," +
                    "P/L Diff," +
                    "Margin Req," +
                    "Mark Value"
            ).split(SPLIT.toString());

    private static final String[] ORDER_HISTORY = (
            "Notes," +
                    "Time Placed," +
                    "Spread," +
                    "Side," +
                    "Qty," +
                    "Pos Effect," +
                    "Symbol," +
                    "Exp," +
                    "Strike," +
                    "Type," +
                    "PRICE," +
                    " ," +
                    "TIF," +
                    "Status"
    ).split(SPLIT.toString());

    private AccountStatementUtil(File file) {
        this.file = file;
    }

    public static AccountStatementUtil create(String filepath) {
        System.out.println("In factory method");
        File file = new File(filepath);
        return new AccountStatementUtil(file);
    }

    public List<EquityEntry> parseEquityEntries() throws IOException {
        List<String[]> allLines = FileUtility.readFile(file);
        List<String[]> rawEquityEntries = new LinkedList<>();
        int lineCount = 0;
        for (String[] line : allLines) {
            if (checkForStartOfSection(line, EQUITIES_LABEL)) {
                rawEquityEntries = getRawData(allLines.subList(lineCount+2, allLines.size()));
                break;
            }
            lineCount++;
        }
        return createEquityEntries(rawEquityEntries.subList(0, rawEquityEntries.size()-1));
    }

    public List<TradeHistoryEntry> parseTradeHistory() throws IOException {
        List<String[]> allLines = FileUtility.readFile(file);
        List<String[]> rawTradeHistory = new LinkedList<>();
        int lineCount = 0;
        for (String[] line : allLines) {
            if (checkForStartOfSection(line, TRADE_HISTORY_LABEL)) {
                rawTradeHistory = getRawData(allLines.subList(lineCount+2, allLines.size()));
                break;
            }
            lineCount++;
        }
        return createEntryObjects(rawTradeHistory);
    }

    public List<PLEntry> parsePLEntries() throws IOException {
        List<String[]> allLines = FileUtility.readFile(file);
        List<String[]> rawPlEntries = new LinkedList<>();
        int lineCount = 0;
        for (String[] line : allLines) {
            if(checkForStartOfSection(line, PL_LABEL)) {
                rawPlEntries = getRawData(allLines.subList(lineCount+2, allLines.size()));
                break;
            }
            lineCount++;
        }
        return createPlEntryObjects(rawPlEntries.subList(0, rawPlEntries.size()-1));
    }

    public List<CashBalanceEntry> parseCashBalanceEntries() throws IOException {
        List<String[]> allLines = FileUtility.readFile(file);
        List<String[]> rawCashBalances = new LinkedList<>();
        int lineCount = 0;
        for (String[] line : allLines) {
            if(checkForStartOfSection(line, CASH_BALANCE_LABEL)) {
                rawCashBalances = getRawData(allLines.subList(lineCount+2, allLines.size()));
                break;
            }
            lineCount++;
        }
        return createCashBalanceObjects(rawCashBalances.subList(0, rawCashBalances.size()-1));
    }

    private List<PLEntry> createPlEntryObjects(List<String[]> rawPlEntries) {
        List<PLEntry> PlEntries = new LinkedList<>();
        for (String[] line : rawPlEntries) {
            try {
                PLEntry entry = extractPlEntryFromRow(line);
                if (entry != null) {
                    PlEntries.add(entry);
                }
            } catch (Exception e) {
                System.out.println("PLEntry :: error parsing Excel file!!!");
            }
        }
        return PlEntries;
    }

    private List<CashBalanceEntry> createCashBalanceObjects(List<String[]> rawCashBalances) {
        List<CashBalanceEntry> cashBalanceEntries = new LinkedList<>();
        for (String[] line : rawCashBalances) {
            try {
                CashBalanceEntry entry = extractCashBalanceFromRow(line);
                if (entry != null) {
                    cashBalanceEntries.add(entry);
                }
            } catch (Exception e) {
                System.out.println("CashBalanceEntry :: error parsing Excel file!!!");
            }
        }
        return cashBalanceEntries;
    }

    private List<TradeHistoryEntry> createEntryObjects(List<String[]> rawTradeHistory) {
        List<TradeHistoryEntry> tradeHistoryEntries = new LinkedList<>();
        for (String[] line : rawTradeHistory) {
            try {
                TradeHistoryEntry entry = extractTradeHistoryFromRow(line);
                if (entry != null) {
                    tradeHistoryEntries.add(entry);
                }
            } catch (Exception e) {
                System.out.println("TradeHistory :: error parsing Excel file!!!");
            }
        }
        return tradeHistoryEntries;
    }


    public List<EquityEntry> createEquityEntries(List<String[]> rawEntries) {
        List<EquityEntry> equityEntryList = new LinkedList<>();
        for (String[] line : rawEntries) {
            try {
                EquityEntry entry = extractEquityEntryFromRow(line);
                if (entry != null) {
                    equityEntryList.add(entry);
                }
            } catch (Exception e) {
                System.out.println("EQUITY :: error parsing Excel file!!!");
            }
        }
        return equityEntryList;
    }

    private PLEntry extractPlEntryFromRow(String[] row) {
        PLEntry.Builder builder = new PLEntry.Builder();
        builder.setSymbol(row[0])
                .setDescription(row[1])
                .setPlOpen(Double.parseDouble(stripSpecialChars(row[2])))
                .setPLPercent(Double.parseDouble(stripSpecialChars(row[3])))
                .setPLDay(Double.parseDouble(stripSpecialChars(row[4])))
                .setPLYear(Double.parseDouble(stripSpecialChars(row[5])))
                .setMarkValue(Double.parseDouble(stripSpecialChars(row[8])));
        return builder.build();
    }

    private EquityEntry extractEquityEntryFromRow(String[] row) {
        EquityEntry.Builder builder = new EquityEntry.Builder();
        builder.setSymbol(row[0])
                .setDescription(row[1])
                .setQuantity(Double.parseDouble(row[2]))
                .setTradePrice(Double.parseDouble(stripSpecialChars(row[3])))
                .setMark(Double.parseDouble(stripSpecialChars(row[4])))
                .setMarkValue(Double.parseDouble(stripSpecialChars(row[5])));
        return builder.build();
    }

    public TradeHistoryEntry extractTradeHistoryFromRow(String[] row) throws Exception {
        if (InstrumentType.fromLabel(row[9]) != null) {
            TradeHistoryEntry.Builder builder = new TradeHistoryEntry.Builder();
            builder.setExecTime(stringToDateTime(row[1]))
                    .setType(InstrumentType.fromLabel(row[9]))
                    .setSide(Side.fromLabel(row[3]))
                    .setQuantity(Integer.parseInt(row[4]))
                    .setEffect(PositionEffect.fromLabel(row[5]))
                    .setSymbol(row[6])
                    .setPrice(Double.parseDouble(row[10]));
            return builder.build();
        }
        return null;
    }

    public CashBalanceEntry extractCashBalanceFromRow(String[] line) {
        CashBalanceEntry.Builder builder = new CashBalanceEntry.Builder();
        builder.addDate(stringToDate(line[0]))
                .addTime(stringToTime(line[1]))
                .addType(CashBalanceCode.fromLabel(line[2]))
                .addDesc(line[4])
                .addBalance(Double.parseDouble(stripSpecialChars(line[8])))
                .addRef(Long.parseLong(line[3].length() > 0 ? line[3] : "0"))
                .addMiscFee(Double.parseDouble(line[5].length() > 0 ? line[5] : "0"))
                .addCommission(Double.parseDouble(line[6].length() > 0 ? line[6] : "0"))
                .addAmount(Double.parseDouble(line[7].length() > 0 ? line[7] : "0"));
        return builder.build();
    }
}

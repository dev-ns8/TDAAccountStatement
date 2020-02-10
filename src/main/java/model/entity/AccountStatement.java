package model.entity;

import model.enums.CashBalanceCode;
import util.AccountStatementUtil;
import util.ParserUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountStatement {

    private final List<CashBalanceEntry> cashBalances;
    private final List<TradeHistoryEntry> tradeHistory;
    private final List<EquityEntry> equityEntries;
    private final List<PLEntry> plEntries;
    private final double totalOpenPl;


    private AccountStatement(List<CashBalanceEntry> cash, List<TradeHistoryEntry> trades, List<EquityEntry> equities, List<PLEntry> pl, double totalOpenPl) {
        this.cashBalances = cash;
        this.tradeHistory = trades;
        this.equityEntries = equities;
        this.plEntries = pl;
        this.totalOpenPl = totalOpenPl;
    }

    public static AccountStatement create(AccountStatementUtil util) throws IOException {
        System.out.println("Creating AccountStatement");
        List<PLEntry> plList = util.parsePLEntries();
        double total = computeTotalOpenPl(plList);
        AccountStatement statement =  new AccountStatement(util.parseCashBalanceEntries(), util.parseTradeHistory(), util.parseEquityEntries(), plList, total);
        return statement;
    }

    private static double computeTotalOpenPl(List<PLEntry> list) {
        double total = 0.0;
        int count = 0;
        for (PLEntry entry : list) {
            total += entry.getPLOpen();
            count++;
        }
        return total / count;
    }
    

    private Map<String, OpenPosition> aggregatePositions(Map<String, OpenPosition> atStart, Map<String, OpenPosition> within) {
        Map<String, OpenPosition> posMap = new HashMap<>();
        for (Map.Entry entry : atStart.entrySet()) {
            String symbol = (String)entry.getKey();
            OpenPosition pos = (OpenPosition) entry.getValue();
            if (posMap.get(symbol) != null) {
                pos.addToQty(within.get(symbol).getQtyAndPrice());
            }
            posMap.put(symbol, pos);
            within.remove(symbol);
        }
        if (within.entrySet().size() > 0) {
            for (Map.Entry entry : within.entrySet()) {
                if (posMap.get(entry.getKey()) != null) {
                    System.out.println("Error combining positions at start with positions within Range");
                }
                posMap.put((String)entry.getKey(), (OpenPosition) entry.getValue());
            }
        }
        return posMap;
    }

    private double computeCash(LocalDate end, List<CashBalanceEntry> list) {
        Optional<CashBalanceEntry> bal = list.stream().filter(entry -> entry.getDate().compareTo(end) == 0 && entry.getType().equals(CashBalanceCode.BAL)).findFirst();
        if (bal.isPresent()) {
            return bal.get().getBalance();
        }
        return 0.0;
    }

    private Map<String, OpenPosition> computePositionsWithinRange(LocalDate start, LocalDate end, List<CashBalanceEntry> trades) {
        Map<String, OpenPosition> positions = new HashMap<>();
        for (CashBalanceEntry entry : trades) {
            if (start.isBefore(entry.getDate())) {
                positions = addToMap(entry, positions);
            }
        }
        return positions;
    }

    private Map<String, OpenPosition> computeOpenPositionsAtStart(LocalDate start, List<CashBalanceEntry> trades) {
        Map<String, OpenPosition> positions = new HashMap<>();
        for (CashBalanceEntry entry : trades) {
            if (start.isAfter(entry.getDate())) {
                positions = addToMap(entry, positions);
            }
        }
        return positions;
    }

    private Map<String, OpenPosition> addToMap(CashBalanceEntry entry, Map<String, OpenPosition> positions) {
        String symbol = ParserUtil.symbolFromDescription(entry.getDescription());
        double qty = ParserUtil.qtyFromDescription(entry.getDescription());
        OpenPosition open = positions.get(symbol);
        if (open != null) {
            positions.put(symbol, open.addToQty(qty, entry.getAmount()));
        } else {
            OpenPosition pos = new OpenPosition(symbol, qty, entry.getAmount());
            positions.put(symbol, pos);
        }
        OpenPosition edited = positions.get(symbol);
        if (OpenPosition.sumQty(edited.getQtyAndPrice()) <= 0) {
            positions.remove(symbol);
        }
        return positions;
    }
}

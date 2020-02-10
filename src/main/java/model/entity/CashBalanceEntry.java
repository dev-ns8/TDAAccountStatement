package model.entity;

import lombok.Getter;
import model.enums.CashBalanceCode;

import java.time.LocalDate;
import java.time.LocalTime;

public class CashBalanceEntry {

    @Getter private LocalDate date;
    @Getter private LocalTime time;
    @Getter private long refNum;
    @Getter private String description;
    @Getter private double fees;
    @Getter private double commission;
    @Getter private double amount;
    @Getter private double balance;
    @Getter private CashBalanceCode type;

    private CashBalanceEntry(LocalDate date, LocalTime time, long refNum, String description, double fees, double commission, double amount, double balance, CashBalanceCode type) {
        this.date = date;
        this.time = time;
        this.refNum = refNum;
        this.description = description;
        this.fees = fees;
        this.commission = commission;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
    }

    public static CashBalanceEntry of(LocalDate date, LocalTime time, long refNum, String description, double fees, double commission, double amount, double balance, CashBalanceCode type) {
        return new CashBalanceEntry(date, time, refNum, description, fees, commission, amount, balance, type);
    }

    public final static class Builder {

        private LocalDate date;
        private LocalTime time;
        private String description;
        private double balance;
        private long refNum = 0L;
        private double miscFee = 0;
        private double commission = 0;
        private double amount = 0;
        private CashBalanceCode type;

        public Builder() {
        }

        public CashBalanceEntry build() {
            return CashBalanceEntry.of(date, time, refNum, description, miscFee, commission, amount, balance, type);
        }

        public Builder addDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder addTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder addDesc(String desc) {
            this.description = desc;
            return this;
        }

        public Builder addBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder addType(CashBalanceCode type) {
            this.type = type;
            return this;
        }

        public Builder addRef(long refNum) {
            this.refNum = refNum;
            return this;
        }

        public Builder addMiscFee(double miscFee) {
            this.miscFee = miscFee;
            return this;
        }

        public Builder addCommission(double commission) {
            this.commission = commission;
            return this;
        }

        public Builder addAmount(double amount) {
            this.amount = amount;
            return this;
        }
    }
}

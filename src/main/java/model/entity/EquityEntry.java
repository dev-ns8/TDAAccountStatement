package model.entity;

import lombok.Getter;

public class EquityEntry {

    private final String symbol;
    private final String description;
    private final double quantity;
    private final double tradePrice;
    private final double mark;
    private final double markValue;

    private EquityEntry(Builder builder) {
        this.symbol = builder.getSymbol();
        this.description = builder.getDescription();
        this.quantity = builder.getQuantity();
        this.tradePrice = builder.getTradePrice();
        this.mark = builder.getMark();
        this.markValue = builder.getMarkValue();
    }

    public static class Builder {

        @Getter private String symbol;
        @Getter private String description;
        @Getter private double quantity;
        @Getter private double tradePrice;
        @Getter private double mark;
        @Getter private double markValue;

        public Builder() {
        }

        public EquityEntry build() {
            return new EquityEntry(this);
        }

        public Builder setSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder setDescription(String desc) {
            this.description = desc;
            return this;
        }

        public Builder setQuantity(double qty) {
            this.quantity = qty;
            return this;
        }

        public Builder setTradePrice(double price) {
            this.tradePrice = price;
            return this;
        }

        public Builder setMark(double mark) {
            this.mark = mark;
            return this;
        }

        public Builder setMarkValue(double markValue) {
            this.markValue = markValue;
            return this;
        }
    }
}

package model.entity;

import lombok.Getter;
import model.enums.OrderStatus;
import model.enums.PositionEffect;
import model.enums.Side;
import model.enums.InstrumentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TradeHistoryEntry {

    @Getter private final LocalDateTime execTime;
    @Getter private final InstrumentType type;
    @Getter private final Side side;
    @Getter private final int quantity;
    @Getter private final PositionEffect effect;
    @Getter private final String symbol;
    @Getter private final LocalDate expiration;
    @Getter private final Double strike;
    @Getter private final OrderStatus status;
    @Getter private final double price;

    private TradeHistoryEntry(LocalDateTime execTime, InstrumentType type, Side side, int quantity, PositionEffect effect, String symbol, OrderStatus status, double price) {
        this.execTime = execTime;
        this.type = type;
        this.side = side;
        this.quantity = quantity;
        this.effect = effect;
        this.symbol = symbol;
        this.status = status;
        this.price = price;
        this.expiration = null;
        this.strike = null;
    }

    private TradeHistoryEntry(Builder builder) {
        this.execTime = builder.getExecTime();
        this.type = builder.getType();
        this.side = builder.getSide();
        this.quantity = builder.getQuantity();
        this.effect = builder.getEffect();
        this.symbol = builder.getSymbol();
        this.expiration = builder.getExpiration();
        this.strike = builder.getStrike();
        this.status = builder.getStatus();
        this.price = builder.getPrice();
    }

    public static class Builder {

        @Getter private LocalDateTime execTime;
        @Getter private InstrumentType type;
        @Getter private Side side;
        @Getter private int quantity;
        @Getter private PositionEffect effect;
        @Getter private String symbol;
        @Getter private LocalDate expiration;
        @Getter private Double strike;
        @Getter private OrderStatus status;
        @Getter private double price;

        public Builder() {
        }

        public TradeHistoryEntry build() {
            if (!type.equals(InstrumentType.OPTION)) {

            }
            return new TradeHistoryEntry(this);
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setExecTime(LocalDateTime execTime) {
            this.execTime = execTime;
            return this;
        }

        public Builder setType(InstrumentType type) {
            this.type = type;
            return this;
        }

        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setEffect(PositionEffect effect) {
            this.effect = effect;
            return this;
        }

        public Builder setSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder setExpiration(LocalDate expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder setStrike(Double strike) {
            this.strike = strike;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }
    }
}

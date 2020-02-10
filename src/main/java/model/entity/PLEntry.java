package model.entity;

import lombok.Getter;

public class PLEntry {

    @Getter private final String symbol;
    @Getter private final String description;
    @Getter private final double PLOpen;
    @Getter private final double PLPercent;
    @Getter private final double PLDay;
    @Getter private final double PLYear;
    @Getter private final double markValue;

    private PLEntry(Builder builder) {
        this.symbol = builder.getSymbol();
        this.description = builder.getDescription();
        this.PLOpen = builder.getPLOpen();
        this.PLPercent = builder.getPLPercent();
        this.PLDay = builder.getPLDay();
        this.PLYear = builder.getPLYear();
        this.markValue = builder.getMarkValue();
    }

    public static class Builder {

        @Getter private String symbol;
        @Getter private String description;
        @Getter private double PLOpen;
        @Getter private double PLPercent;
        @Getter private double PLDay;
        @Getter private double PLYear;
        @Getter private double markValue;

        public Builder() {
        }

        public PLEntry build() {
            return new PLEntry(this);
        }

        public Builder setSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder setDescription(String desc) {
            this.description = desc;
            return this;
        }

        public Builder setPlOpen(double open) {
            this.PLOpen = open;
            return this;
        }

        public Builder setPLPercent(double percent) {
            this.PLPercent = percent;
            return this;
        }

        public Builder setPLDay(double day) {
            this.PLDay = day;
            return this;
        }

        public Builder setPLYear(double year) {
            this.PLYear = year;
            return this;
        }

        public Builder setMarkValue(double value) {
            this.markValue = value;
            return this;
        }
    }

}

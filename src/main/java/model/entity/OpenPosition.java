package model.entity;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OpenPosition {

    @Getter private final String symbol;
    @Getter private List<Pair<Double, Double>> qtyAndPrice = new LinkedList<>();

    public OpenPosition(String symbol, double qty, double cost) {
        this.symbol = symbol;
        qtyAndPrice.add(Pair.of(qty, cost));
    }

    public OpenPosition addToQty(double qty, double price) {
        qtyAndPrice.add(Pair.of(qty, price));
        return this;
    }

    public OpenPosition addToQty(List<Pair<Double, Double>> list) {
        qtyAndPrice.addAll(list);
        return this;
    }

    public double computeAverageCost() {
        double qty = 0.0;
        double price = 0.0;
        for (Pair pair : qtyAndPrice) {
            qty += (double)pair.getFirst();
            price += (double)pair.getSecond();
        }
        return price / qty;
    }

    public static double computeAverageCost(double qty, double totalPrice) {
        return totalPrice / qty;
    }

    public static double sumQty(List<Pair<Double, Double>> qtyAndPrice) {
        return qtyAndPrice.stream().map(Pair::getFirst).collect(Collectors.summingDouble(Double::doubleValue));
    }

}

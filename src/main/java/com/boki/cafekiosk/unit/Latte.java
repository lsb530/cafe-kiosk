package com.boki.cafekiosk.unit;

public class Latte implements Beverage {
    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4_500;
    }
}

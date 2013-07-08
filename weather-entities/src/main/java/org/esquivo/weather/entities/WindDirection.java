package org.esquivo.weather.entities;

public enum WindDirection {
    N(0), 
    S(180), 
    E(90), 
    W(270),
    NE(45), 
    NW(315), 
    SE(225), 
    SW(135);

    private int degrees;

    private WindDirection(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return this.degrees;
    }
}

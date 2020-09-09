package net.chibidevteam.bam;

public enum Currency {
    DOLLAR("$"), EURO("€");

    private String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

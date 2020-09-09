package net.chibidevteam.bam;

public enum Currency {
    DOLLAR("$"), EURO("€");

    private String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public static Currency fromSymbol(String symbol) {
        for (Currency currency : Currency.values()) {
            if (currency.getSymbol().equals(symbol)) {
                return currency;
            }
        }

        return null;
    }

    public String getSymbol() {
        return symbol;
    }
}

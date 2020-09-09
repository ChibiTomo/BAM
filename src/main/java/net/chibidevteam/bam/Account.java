package net.chibidevteam.bam;

public class Account {
    private int balance;
    private Currency currency;

    public Account(int defaultBalance, Currency currency) {
        balance = defaultBalance;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getBalance() {
        return balance + currency.getSymbol();
    }

    public void add(int amount) {
        balance += amount;
    }

    public void retrieve(int amount) {
        balance-=amount;
    }
}

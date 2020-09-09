package net.chibidevteam.bam;

import net.chibidevteam.bam.exceptions.WrongCurrencyException;

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

    public void add(int amount, Currency currency) throws WrongCurrencyException {
        if (!this.currency.equals(currency)) {
            throw new WrongCurrencyException("Trying to add " + currency + " to a " + this.currency + " account.");
        }
        balance += amount;
    }

    public void retrieve(int amount, Currency currency) throws WrongCurrencyException {
        if (!this.currency.equals(currency)) {
            throw new WrongCurrencyException("Trying to retrieve " + currency + " from a " + this.currency + " account.");
        }
        balance -= amount;
    }
}

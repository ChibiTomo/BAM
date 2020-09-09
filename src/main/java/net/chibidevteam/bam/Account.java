package net.chibidevteam.bam;

import net.chibidevteam.bam.exceptions.WrongCurrencyException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private Currency currency;
    private List<String> historic = new ArrayList<>();

    public Account(int defaultBalance, Currency currency) {
        balance = defaultBalance;
        this.currency = currency;

        historic.add("Creation: " + this.balance + this.currency.getSymbol());
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getBalance() {
        return balance + currency.getSymbol();
    }

    public void credit(int amount, Currency currency) throws WrongCurrencyException {
        if (!this.currency.equals(currency)) {
            throw new WrongCurrencyException("Trying to credit " + currency + " to a " + this.currency + " account.");
        }
        balance += amount;
        historic.add("Credit: " + amount + this.currency.getSymbol());
    }

    public void debit(int amount, Currency currency) throws WrongCurrencyException {
        if (!this.currency.equals(currency)) {
            throw new WrongCurrencyException("Trying to debit " + currency + " from a " + this.currency + " account.");
        }
        balance -= amount;
        historic.add("Debit: " + amount + this.currency.getSymbol());
    }

    public List<String> getHistoric() {
        return historic;
    }
}

package net.chibidevteam.bam;

import lombok.SneakyThrows;
import net.chibidevteam.bam.exceptions.WrongCurrencyException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class AccountTest {

    @Test
    public void should_currency_be_dollar() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN
        Currency currency = account.getCurrency();

        // THEN
        Currency expected = Currency.DOLLAR;
        assertThat(currency).isEqualTo(expected);
    }

    @Test
    public void should_currency_be_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        Currency currency = account.getCurrency();

        // THEN
        Currency expected = Currency.EURO;
        assertThat(currency).isEqualTo(expected);
    }

    @Test
    public void should_default_balance_be_20_dollar() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN
        String balance = account.getBalance();

        // THEN
        String expected = "20" + Currency.DOLLAR.getSymbol();
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    public void should_default_balance_be_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        String balance = account.getBalance();

        // THEN
        String expected = "20" + Currency.EURO.getSymbol();
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void should_balance_be_30_euro_after_adding_10_euro_with_starting_balance_of_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        account.add(10, Currency.EURO);
        String balance = account.getBalance();

        // THEN
        String expected = "30" + Currency.EURO.getSymbol();
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void should_balance_be_10_euro_after_retrieving_10_euro_with_starting_balance_of_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        account.retrieve(10, Currency.EURO);
        String balance = account.getBalance();

        // THEN
        String expected = "10" + Currency.EURO.getSymbol();
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    public void should_throw_when_adding_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.assertThatThrownBy(() -> {
            // WHEN
            account.add(10, Currency.DOLLAR);
        })

                // THEN
                .isInstanceOf(WrongCurrencyException.class);
    }

    @Test
    public void should_not_change_balance_when_adding_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.catchThrowable(() -> {
            // WHEN
            account.add(10, Currency.DOLLAR);
        });

        // THEN
        String expected = "20"+Currency.EURO.getSymbol();
        assertThat(account.getBalance()).isEqualTo(expected);
    }

    @Test
    public void should_throw_when_retrieving_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.assertThatThrownBy(() -> {
            // WHEN
            account.retrieve(10, Currency.DOLLAR);
        })

                // THEN
                .isInstanceOf(WrongCurrencyException.class);
    }

    @Test
    public void should_not_change_balance_when_retrieving_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.catchThrowable(() -> {
            // WHEN
            account.retrieve(10, Currency.DOLLAR);
        });

        // THEN
        String expected = "20"+Currency.EURO.getSymbol();
        assertThat(account.getBalance()).isEqualTo(expected);
    }

}
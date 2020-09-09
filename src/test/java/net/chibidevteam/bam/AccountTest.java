package net.chibidevteam.bam;

import lombok.SneakyThrows;
import net.chibidevteam.bam.exceptions.WrongCurrencyException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String expected = "20$";
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    public void should_default_balance_be_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        String balance = account.getBalance();

        // THEN
        String expected = "20€";
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void should_balance_be_30_euro_after_crediting_10_euro_with_starting_balance_of_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        account.credit(10, Currency.EURO);
        String balance = account.getBalance();

        // THEN
        String expected = "30€";
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void should_balance_be_10_euro_after_retrieving_10_euro_with_starting_balance_of_20_euro() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        // WHEN
        account.debit(10, Currency.EURO);
        String balance = account.getBalance();

        // THEN
        String expected = "10€";
        assertThat(balance).isEqualTo(expected);
    }

    @Test
    public void should_throw_when_crediting_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.assertThatThrownBy(() -> {
            // WHEN
            account.credit(10, Currency.DOLLAR);
        })

                // THEN
                .isInstanceOf(WrongCurrencyException.class);
    }

    @Test
    public void should_not_change_balance_when_crediting_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.catchThrowable(() -> {
            // WHEN
            account.credit(10, Currency.DOLLAR);
        });

        // THEN
        String expected = "20€";
        assertThat(account.getBalance()).isEqualTo(expected);
    }

    @Test
    public void should_throw_when_retrieving_wrong_currency() {
        // GIVEN
        Account account = new Account(20, Currency.EURO);

        Assertions.assertThatThrownBy(() -> {
            // WHEN
            account.debit(10, Currency.DOLLAR);
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
            account.debit(10, Currency.DOLLAR);
        });

        // THEN
        String expected = "20€";
        assertThat(account.getBalance()).isEqualTo(expected);
    }

    @Test
    public void should_save_historic_on_account_creation() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN

        // THEN
        List<String> expected = Arrays.asList("Creation: 20$");
        assertThat(account.getHistoric()).containsExactlyElementsOf(expected);
    }

    @Test
    @SneakyThrows
    public void should_save_credit_in_historic() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN
        account.credit(10, Currency.DOLLAR);

        // THEN
        List<String> expected = Arrays.asList("Creation: 20$", "Credit: 10$");
        assertThat(account.getHistoric()).containsExactlyElementsOf(expected);
    }

    @Test
    @SneakyThrows
    public void should_save_debit_in_historic() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN
        account.debit(10, Currency.DOLLAR);

        // THEN
        List<String> expected = Arrays.asList("Creation: 20$", "Debit: 10$");
        assertThat(account.getHistoric()).containsExactlyElementsOf(expected);
    }

    @Test
    @SneakyThrows
    public void should_save_all_actions_in_correct_order_in_historic() {
        // GIVEN
        Account account = new Account(20, Currency.DOLLAR);

        // WHEN
        account.credit(10, Currency.DOLLAR);
        account.debit(20, Currency.DOLLAR);
        account.credit(50, Currency.DOLLAR);
        account.debit(15, Currency.DOLLAR);

        // THEN
        List<String> expected = Arrays.asList("Creation: 20$", "Credit: 10$", "Debit: 20$", "Credit: 50$", "Debit: 15$");
        assertThat(account.getHistoric()).containsExactlyElementsOf(expected);
    }

}
package net.chibidevteam.bam;

import net.chibidevteam.bam.exceptions.WrongCurrencyException;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
    static Pattern amountPattern = Pattern.compile("^(\\d+)([\\$€])$");
    private static List<Account> accounts = new ArrayList<>();
    private static Integer selectedAccountID = null;

    public static void main(String[] args) throws IOException {
        String line = null;
        do {
            if (StringUtils.isNotEmpty(line)) {
                switch (line.trim()) {
                    case "1":
                        createAccount();
                        break;
                    case "2":
                        selectAccount();
                        break;
                    case "3":
                        creditAccount();
                        break;
                    case "4":
                        debitAccount();
                        break;
                    case "5":
                        printHistory();
                        break;
                    case "6":
                        // Quit
                        return;
                    default:
                }
            }
            printMainMenu();
        } while ((line = readLine()) != null);
    }


    private static void createAccount() throws IOException {
        System.out.print("Please enter the starting balance of the account (empty to cancel): ");
        String balance = readLine();
        if (StringUtils.isEmpty(balance)) {
            return;
        }

        Matcher matcher = amountPattern.matcher(balance);
        if (matcher.matches()) {
            accounts.add(new Account(Integer.valueOf(matcher.group(1)), Currency.fromSymbol(matcher.group(2))));
        } else {
            System.out.println("Could not understand entry...");
            createAccount();
        }
    }

    private static void selectAccount() throws IOException {
        if (accounts.size() == 0) {
            System.out.println("Sorry... No account to select.");
            return;
        }

        for (int i = 0; i < accounts.size(); ++i) {
            System.out.println(i + "/ " + accounts.get(i).getBalance());
        }
        System.out.print("Please select an account (empty to cancel): ");
        String id = readLine();
        if (StringUtils.isEmpty(id)) {
            return;
        }

        int idx = -1;
        try {
            idx = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            System.out.println("Could not understand entry...");
            selectAccount();
            return;
        }

        if (idx < 0 || idx >= accounts.size()) {
            System.out.println("Could not understand entry...");
            selectAccount();
            return;
        }

        selectedAccountID = idx;
    }

    private static void creditAccount() throws IOException {
        modifyAccountBalance(true);
    }

    private static void debitAccount() throws IOException {
        modifyAccountBalance(false);
    }

    private static void modifyAccountBalance(boolean isCredit) throws IOException {
        if (selectedAccountID == null) {
            System.out.println("Please select an account before.");
            return;
        }

        if (isCredit) {
            System.out.print("Please enter the amount you want to credit (empty to cancel): ");
        } else {
            System.out.print("Please enter the amount you want to debit (empty to cancel): ");
        }

        String amountStr = readLine();
        if (StringUtils.isEmpty(amountStr)) {
            return;
        }

        Matcher matcher = amountPattern.matcher(amountStr);
        if (matcher.matches()) {
            Account account = accounts.get(selectedAccountID);
            Integer amount = Integer.valueOf(matcher.group(1));
            Currency currency = Currency.fromSymbol(matcher.group(2));
            try {
                if (isCredit) {
                    account.credit(amount, currency);
                } else {
                    account.debit(amount, currency);
                }
            } catch (WrongCurrencyException e) {
                System.out.println(e.getMessage());
                modifyAccountBalance(isCredit);
            }
        } else {
            System.out.println("Could not understand entry...");
            modifyAccountBalance(isCredit);
        }
    }

    private static void printHistory() {
        if (selectedAccountID == null) {
            System.out.println("Please select an account before.");
            return;
        }

        Account account = accounts.get(selectedAccountID);
        System.out.println("History of account " + selectedAccountID + " " + account.getBalance());
        List<String> history = account.getHistoric();
        for (String line : history) {
            System.out.println(line);
        }

    }

    private static void printMainMenu() {
        System.out.println("1/ Create new account");
        System.out.println("2/ Select account");
        System.out.println("3/ Credit");
        System.out.println("4/ Debit");
        System.out.println("5/ Historic");
        System.out.println("6/ Quit");
        if (selectedAccountID != null) {
            System.out.println("Account selected: " + selectedAccountID + ": " + accounts.get(selectedAccountID).getBalance());
        }
        System.out.print("Please choose an action: ");
    }

    public static String readLine() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}

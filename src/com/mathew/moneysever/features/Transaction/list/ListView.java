package com.mathew.moneysever.features.Transaction.list;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.Transaction;
import com.mathew.moneysever.util.ConsoleInput;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ListView {

    private final ListModel model = new ListModel();

    public void show(Long userId) {
        while (true) {
            System.out.println();
            System.out.println("-- Transactions --");
            System.out.println("1. View all");
            System.out.println("2. Search");
            System.out.println("3. Filter");
            System.out.println("4. Back");
            int choice = ConsoleInput.readInt("Choose option: ");

            if (choice == 1) {
                printTransactions(model.getTransactions(userId));
            } else if (choice == 2) {
                String query = ConsoleInput.readLine("Search category/description: ");
                printTransactions(model.search(userId, query));
            } else if (choice == 3) {
                printTransactions(readAndFilter(userId));
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public void printTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("ID | Date | Type | Amount | Category | Description");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getId() + " | "
                    + MoneyServerDB.toLocalDate(transaction.getDate()) + " | "
                    + transaction.getType() + " | "
                    + String.format("%.2f", transaction.getAmount()) + " | "
                    + transaction.getCategory() + " | "
                    + transaction.getDescription());
        }
    }

    private List<Transaction> readAndFilter(Long userId) {
        Transaction.TransactionType type = null;
        System.out.println("0. Any");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        int typeChoice = ConsoleInput.readInt("Choose type: ");
        if (typeChoice == 1) {
            type = Transaction.TransactionType.INCOME;
        } else if (typeChoice == 2) {
            type = Transaction.TransactionType.EXPENSE;
        }

        String category = ConsoleInput.readLine("Category contains (blank for any): ");
        LocalDate fromDate = readOptionalDate("From date yyyy-MM-dd (blank for any): ");
        LocalDate toDate = readOptionalDate("To date yyyy-MM-dd (blank for any): ");
        return model.filter(userId, type, category, fromDate, toDate);
    }

    private LocalDate readOptionalDate(String prompt) {
        while (true) {
            String value = ConsoleInput.readLine(prompt);
            if (value.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Use yyyy-MM-dd format.");
            }
        }
    }
}

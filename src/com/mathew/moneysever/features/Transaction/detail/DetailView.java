package com.mathew.moneysever.features.Transaction.detail;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.RecurringTransaction;
import com.mathew.moneysever.data.dto.Transaction;
import com.mathew.moneysever.util.ConsoleInput;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DetailView {

    private final DetailModel model = new DetailModel();

    public void showAddTransaction(Long userId) {
        System.out.println();
        System.out.println("-- Add Transaction --");
        Transaction.TransactionType type = readType();
        double amount = ConsoleInput.readDouble("Amount: ");
        String category = ConsoleInput.readLine("Category: ");
        String description = ConsoleInput.readLine("Description: ");
        LocalDate date = readDate("Date (yyyy-MM-dd, blank for today): ");

        Transaction transaction = model.add(userId, type, amount, category, description,
                MoneyServerDB.toEpochMillis(date));
        System.out.println("Transaction added with id " + transaction.getId() + ".");
    }

    public void showRecurringTransactions(Long userId) {
        while (true) {
            System.out.println();
            System.out.println("-- Recurring Transactions --");
            System.out.println("1. View recurring");
            System.out.println("2. Add recurring");
            System.out.println("3. Back");
            int choice = ConsoleInput.readInt("Choose option: ");

            if (choice == 1) {
                printRecurring(userId);
            } else if (choice == 2) {
                addRecurring(userId);
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public Transaction.TransactionType readType() {
        while (true) {
            System.out.println("1. Income");
            System.out.println("2. Expense");
            int choice = ConsoleInput.readInt("Choose type: ");
            if (choice == 1) {
                return Transaction.TransactionType.INCOME;
            }
            if (choice == 2) {
                return Transaction.TransactionType.EXPENSE;
            }
            System.out.println("Invalid type.");
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            String value = ConsoleInput.readLine(prompt);
            if (value.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Use yyyy-MM-dd format.");
            }
        }
    }

    private void addRecurring(Long userId) {
        Transaction.TransactionType type = readType();
        double amount = ConsoleInput.readDouble("Amount: ");
        String category = ConsoleInput.readLine("Category: ");
        String description = ConsoleInput.readLine("Description: ");
        RecurringTransaction.Frequency frequency = readFrequency();
        LocalDate nextRunDate = readDate("First run date (yyyy-MM-dd, blank for today): ");

        RecurringTransaction recurringTransaction = model.addRecurring(userId, type, amount, category,
                description, frequency, MoneyServerDB.toEpochMillis(nextRunDate));
        System.out.println("Recurring transaction added with id " + recurringTransaction.getId() + ".");
    }

    private RecurringTransaction.Frequency readFrequency() {
        while (true) {
            System.out.println("1. Daily");
            System.out.println("2. Weekly");
            System.out.println("3. Monthly");
            int choice = ConsoleInput.readInt("Choose frequency: ");
            if (choice == 1) {
                return RecurringTransaction.Frequency.DAILY;
            }
            if (choice == 2) {
                return RecurringTransaction.Frequency.WEEKLY;
            }
            if (choice == 3) {
                return RecurringTransaction.Frequency.MONTHLY;
            }
            System.out.println("Invalid frequency.");
        }
    }

    private void printRecurring(Long userId) {
        List<RecurringTransaction> recurringTransactions = model.getRecurring(userId);
        if (recurringTransactions.isEmpty()) {
            System.out.println("No recurring transactions found.");
            return;
        }
        System.out.println("ID | Next Run | Frequency | Type | Amount | Category | Description");
        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            System.out.println(recurringTransaction.getId() + " | "
                    + MoneyServerDB.toLocalDate(recurringTransaction.getNextRunDate()) + " | "
                    + recurringTransaction.getFrequency() + " | "
                    + recurringTransaction.getType() + " | "
                    + String.format("%.2f", recurringTransaction.getAmount()) + " | "
                    + recurringTransaction.getCategory() + " | "
                    + recurringTransaction.getDescription());
        }
    }
}

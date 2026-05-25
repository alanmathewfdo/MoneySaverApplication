package com.mathew.moneysever.data.Repository;

import com.mathew.moneysever.data.dto.RecurringTransaction;
import com.mathew.moneysever.data.dto.SavingGoal;
import com.mathew.moneysever.data.dto.Transaction;
import com.mathew.moneysever.data.dto.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MoneyServerDB {

    private static final List<User> USERS = new ArrayList<User>();
    private static final List<Transaction> TRANSACTIONS = new ArrayList<Transaction>();
    private static final List<SavingGoal> SAVING_GOALS = new ArrayList<SavingGoal>();
    private static final List<RecurringTransaction> RECURRING_TRANSACTIONS = new ArrayList<RecurringTransaction>();

    private static long nextUserId = 1;
    private static long nextTransactionId = 1;
    private static long nextSavingGoalId = 1;
    private static long nextRecurringTransactionId = 1;

    private MoneyServerDB() {
    }

    public static User addUser(String name, String email, String password) {
        User user = new User(nextUserId++, name, email, password);
        USERS.add(user);
        return user;
    }

    public static User findUserByEmail(String email) {
        for (User user : USERS) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public static User authenticate(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static Transaction addTransaction(Long userId, Transaction.TransactionType type, double amount,
                                             String category, String description, long date,
                                             Long recurringTransactionId) {
        Transaction transaction = new Transaction();
        transaction.setId(nextTransactionId++);
        transaction.setUserId(userId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        transaction.setRecurringTransactionId(recurringTransactionId);
        TRANSACTIONS.add(transaction);
        return transaction;
    }

    public static List<Transaction> getTransactionsForUser(Long userId) {
        List<Transaction> result = new ArrayList<Transaction>();
        for (Transaction transaction : TRANSACTIONS) {
            if (transaction.getUserId().equals(userId)) {
                result.add(transaction);
            }
        }
        result.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction first, Transaction second) {
                return Long.compare(second.getDate(), first.getDate());
            }
        });
        return result;
    }

    public static SavingGoal addSavingGoal(Long userId, String name, double targetAmount, double savedAmount) {
        SavingGoal goal = new SavingGoal();
        goal.setId(nextSavingGoalId++);
        goal.setUserId(userId);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setSavedAmount(savedAmount);
        SAVING_GOALS.add(goal);
        return goal;
    }

    public static List<SavingGoal> getSavingGoalsForUser(Long userId) {
        List<SavingGoal> result = new ArrayList<SavingGoal>();
        for (SavingGoal goal : SAVING_GOALS) {
            if (goal.getUserId().equals(userId)) {
                result.add(goal);
            }
        }
        return result;
    }

    public static SavingGoal findSavingGoal(Long userId, Long goalId) {
        for (SavingGoal goal : SAVING_GOALS) {
            if (goal.getUserId().equals(userId) && goal.getId().equals(goalId)) {
                return goal;
            }
        }
        return null;
    }

    public static RecurringTransaction addRecurringTransaction(Long userId, Transaction.TransactionType type,
                                                              double amount, String category, String description,
                                                              RecurringTransaction.Frequency frequency,
                                                              long nextRunDate) {
        RecurringTransaction recurringTransaction = new RecurringTransaction();
        recurringTransaction.setId(nextRecurringTransactionId++);
        recurringTransaction.setUserId(userId);
        recurringTransaction.setType(type);
        recurringTransaction.setAmount(amount);
        recurringTransaction.setCategory(category);
        recurringTransaction.setDescription(description);
        recurringTransaction.setFrequency(frequency);
        recurringTransaction.setNextRunDate(nextRunDate);
        recurringTransaction.setActive(true);
        RECURRING_TRANSACTIONS.add(recurringTransaction);
        return recurringTransaction;
    }

    public static List<RecurringTransaction> getRecurringTransactionsForUser(Long userId) {
        List<RecurringTransaction> result = new ArrayList<RecurringTransaction>();
        for (RecurringTransaction recurringTransaction : RECURRING_TRANSACTIONS) {
            if (recurringTransaction.getUserId().equals(userId)) {
                result.add(recurringTransaction);
            }
        }
        return result;
    }

    public static int applyDueRecurringTransactions(Long userId) {
        int added = 0;
        LocalDate today = LocalDate.now();
        for (RecurringTransaction recurringTransaction : RECURRING_TRANSACTIONS) {
            if (!recurringTransaction.getUserId().equals(userId) || !recurringTransaction.isActive()) {
                continue;
            }

            LocalDate nextRunDate = toLocalDate(recurringTransaction.getNextRunDate());
            while (!nextRunDate.isAfter(today)) {
                addTransaction(userId, recurringTransaction.getType(), recurringTransaction.getAmount(),
                        recurringTransaction.getCategory(),
                        recurringTransaction.getDescription() + " (Recurring)",
                        toEpochMillis(nextRunDate), recurringTransaction.getId());
                nextRunDate = nextDate(nextRunDate, recurringTransaction.getFrequency());
                added++;
            }
            recurringTransaction.setNextRunDate(toEpochMillis(nextRunDate));
        }
        return added;
    }

    public static long todayMillis() {
        return toEpochMillis(LocalDate.now());
    }

    public static long toEpochMillis(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDate toLocalDate(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static LocalDate nextDate(LocalDate date, RecurringTransaction.Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
            case MONTHLY:
                return date.plusMonths(1);
            default:
                return date.plusMonths(1);
        }
    }
}

package com.mathew.moneysever.features.report;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.Transaction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportModle {

    public double totalIncome(Long userId) {
        return totalByType(userId, Transaction.TransactionType.INCOME);
    }

    public double totalExpense(Long userId) {
        return totalByType(userId, Transaction.TransactionType.EXPENSE);
    }

    public double balance(Long userId) {
        return totalIncome(userId) - totalExpense(userId);
    }

    public Map<String, Double> expenseByCategory(Long userId) {
        Map<String, Double> result = new LinkedHashMap<String, Double>();
        List<Transaction> transactions = MoneyServerDB.getTransactionsForUser(userId);
        for (Transaction transaction : transactions) {
            if (transaction.getType() != Transaction.TransactionType.EXPENSE) {
                continue;
            }
            String category = transaction.getCategory() == null || transaction.getCategory().isEmpty()
                    ? "Uncategorized" : transaction.getCategory();
            Double current = result.get(category);
            result.put(category, (current == null ? 0 : current) + transaction.getAmount());
        }
        return result;
    }

    public String topExpenseCategory(Long userId) {
        String topCategory = "None";
        double topAmount = 0;
        for (Map.Entry<String, Double> entry : expenseByCategory(userId).entrySet()) {
            if (entry.getValue() > topAmount) {
                topAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }
        if (topAmount == 0) {
            return topCategory;
        }
        return topCategory + " (" + String.format("%.2f", topAmount) + ")";
    }

    public String savingInsight(Long userId) {
        double income = totalIncome(userId);
        double expense = totalExpense(userId);
        if (income == 0) {
            return "Add income transactions to calculate a saving rate.";
        }
        double savingRate = ((income - expense) / income) * 100;
        if (savingRate >= 20) {
            return "Saving rate is " + String.format("%.1f", savingRate) + "%. Good progress.";
        }
        if (savingRate >= 0) {
            return "Saving rate is " + String.format("%.1f", savingRate) + "%. Try reducing the top expense category.";
        }
        return "Expenses are higher than income by " + String.format("%.2f", expense - income) + ".";
    }

    private double totalByType(Long userId, Transaction.TransactionType type) {
        double total = 0;
        List<Transaction> transactions = MoneyServerDB.getTransactionsForUser(userId);
        for (Transaction transaction : transactions) {
            if (transaction.getType() == type) {
                total += transaction.getAmount();
            }
        }
        return total;
    }
}

package com.mathew.moneysever.features.Transaction.list;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListModel {

    public List<Transaction> getTransactions(Long userId) {
        return MoneyServerDB.getTransactionsForUser(userId);
    }

    public List<Transaction> search(Long userId, String query) {
        String normalizedQuery = query.toLowerCase();
        List<Transaction> result = new ArrayList<Transaction>();
        for (Transaction transaction : getTransactions(userId)) {
            String category = safe(transaction.getCategory()).toLowerCase();
            String description = safe(transaction.getDescription()).toLowerCase();
            if (category.contains(normalizedQuery) || description.contains(normalizedQuery)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<Transaction> filter(Long userId, Transaction.TransactionType type, String category,
                                    LocalDate fromDate, LocalDate toDate) {
        List<Transaction> result = new ArrayList<Transaction>();
        String normalizedCategory = safe(category).toLowerCase();
        for (Transaction transaction : getTransactions(userId)) {
            if (type != null && transaction.getType() != type) {
                continue;
            }
            if (!normalizedCategory.isEmpty()
                    && !safe(transaction.getCategory()).toLowerCase().contains(normalizedCategory)) {
                continue;
            }
            LocalDate transactionDate = MoneyServerDB.toLocalDate(transaction.getDate());
            if (fromDate != null && transactionDate.isBefore(fromDate)) {
                continue;
            }
            if (toDate != null && transactionDate.isAfter(toDate)) {
                continue;
            }
            result.add(transaction);
        }
        return result;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

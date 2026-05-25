package com.mathew.moneysever.features.Transaction.detail;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.RecurringTransaction;
import com.mathew.moneysever.data.dto.Transaction;

import java.util.List;

public class DetailModel {

    public Transaction add(Long userId, Transaction.TransactionType type, double amount,
                           String category, String description, long date) {
        return MoneyServerDB.addTransaction(userId, type, amount, category, description, date, null);
    }

    public RecurringTransaction addRecurring(Long userId, Transaction.TransactionType type, double amount,
                                             String category, String description,
                                             RecurringTransaction.Frequency frequency, long nextRunDate) {
        return MoneyServerDB.addRecurringTransaction(userId, type, amount, category, description, frequency, nextRunDate);
    }

    public List<RecurringTransaction> getRecurring(Long userId) {
        return MoneyServerDB.getRecurringTransactionsForUser(userId);
    }
}

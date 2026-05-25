package com.mathew.moneysever.features.report;

import java.util.Map;

public class ReportView {

    private final ReportModle model = new ReportModle();

    public void show(Long userId) {
        System.out.println();
        System.out.println("-- Expense Insights --");
        System.out.println("Total income : " + String.format("%.2f", model.totalIncome(userId)));
        System.out.println("Total expense: " + String.format("%.2f", model.totalExpense(userId)));
        System.out.println("Balance      : " + String.format("%.2f", model.balance(userId)));
        System.out.println("Top expense  : " + model.topExpenseCategory(userId));
        System.out.println("Insight      : " + model.savingInsight(userId));

        Map<String, Double> expenseByCategory = model.expenseByCategory(userId);
        if (!expenseByCategory.isEmpty()) {
            System.out.println();
            System.out.println("Expense by category");
            for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
                System.out.println(entry.getKey() + ": " + String.format("%.2f", entry.getValue()));
            }
        }
    }
}

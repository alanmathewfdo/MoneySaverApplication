package com.mathew.moneysever;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.User;
import com.mathew.moneysever.features.Transaction.list.ListView;
import com.mathew.moneysever.features.report.ReportView;
import com.mathew.moneysever.features.signin.SignInView;
import com.mathew.moneysever.features.signup.SignUpView;
import com.mathew.moneysever.util.ConsoleInput;

public class MSApplication {

    public static final int VERSION_NO = 1;
    public static final String VERSION_NAME = "ms1.0.0";

    public static void main(String[] args) {

        System.out.println("-- Welcome To Money Saver Application ! --");
        System.out.println("Version Name : " + VERSION_NAME);
        handleAuthSelection();
    }

    private static void handleAuthSelection() {

        while (true) {

            System.out.println();
            System.out.println("1. Sign In ");
            System.out.println("2. Sign Up ");
            System.out.println("3. Exit");
            int choice = ConsoleInput.readInt("Choose the option: ");

            User user = null;
            if (choice == 1) {
                user = new SignInView().show();
            } else if (choice == 2) {
                user = new SignUpView().show();
            } else if (choice == 3) {
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Invalid option.");
            }

            if (user != null) {
                showDashboard(user);
            }
        }
    }

    private static void showDashboard(User user) {
        com.mathew.moneysever.features.Transaction.detail.DetailView transactionDetailView = new com.mathew.moneysever.features.Transaction.detail.DetailView();
        ListView listView = new ListView();
        ReportView reportView = new ReportView();
        com.mathew.moneysever.features.user.detail.DetailView userDetailView = new com.mathew.moneysever.features.user.detail.DetailView();

        while (true) {
            int addedRecurring = MoneyServerDB.applyDueRecurringTransactions(user.getId());
            if (addedRecurring > 0) {
                System.out.println(addedRecurring + " recurring transaction(s) auto-added.");
            }

            System.out.println();
            System.out.println("-- Dashboard: " + user.getName() + " --");
            System.out.println("1. Add transaction");
            System.out.println("2. Search and filter transactions");
            System.out.println("3. Expense insights");
            System.out.println("4. Saving goals");
            System.out.println("5. Recurring transactions");
            System.out.println("6. Logout");
            int choice = ConsoleInput.readInt("Choose option: ");

            if (choice == 1) {
                transactionDetailView.showAddTransaction(user.getId());
            } else if (choice == 2) {
                listView.show(user.getId());
            } else if (choice == 3) {
                reportView.show(user.getId());
            } else if (choice == 4) {
                userDetailView.showSavingGoals(user.getId());
            } else if (choice == 5) {
                transactionDetailView.showRecurringTransactions(user.getId());
            } else if (choice == 6) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}

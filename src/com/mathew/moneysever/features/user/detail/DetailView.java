package com.mathew.moneysever.features.user.detail;

import com.mathew.moneysever.data.dto.SavingGoal;
import com.mathew.moneysever.util.ConsoleInput;

import java.util.List;

public class DetailView {

    private final DetailModel model = new DetailModel();

    public void showSavingGoals(Long userId) {
        while (true) {
            System.out.println();
            System.out.println("-- Saving Goals --");
            System.out.println("1. View goals");
            System.out.println("2. Add goal");
            System.out.println("3. Add saved amount");
            System.out.println("4. Back");
            int choice = ConsoleInput.readInt("Choose option: ");

            if (choice == 1) {
                printGoals(userId);
            } else if (choice == 2) {
                addGoal(userId);
            } else if (choice == 3) {
                addSavedAmount(userId);
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void addGoal(Long userId) {
        String name = ConsoleInput.readLine("Goal name: ");
        double targetAmount = ConsoleInput.readDouble("Target amount: ");
        double savedAmount = ConsoleInput.readNonNegativeDouble("Already saved amount: ");
        SavingGoal goal = model.addGoal(userId, name, targetAmount, savedAmount);
        System.out.println("Saving goal added with id " + goal.getId() + ".");
    }

    private void addSavedAmount(Long userId) {
        printGoals(userId);
        Long goalId = Long.valueOf(ConsoleInput.readInt("Goal id: "));
        double amount = ConsoleInput.readDouble("Amount to add: ");
        if (model.addSavings(userId, goalId, amount)) {
            System.out.println("Goal updated.");
        } else {
            System.out.println("Goal not found.");
        }
    }

    private void printGoals(Long userId) {
        List<SavingGoal> goals = model.getGoals(userId);
        if (goals.isEmpty()) {
            System.out.println("No saving goals found.");
            return;
        }
        System.out.println("ID | Name | Saved | Target | Progress");
        for (SavingGoal goal : goals) {
            double progress = goal.getTargetAmount() == 0 ? 0 : (goal.getSavedAmount() / goal.getTargetAmount()) * 100;
            System.out.println(goal.getId() + " | "
                    + goal.getName() + " | "
                    + String.format("%.2f", goal.getSavedAmount()) + " | "
                    + String.format("%.2f", goal.getTargetAmount()) + " | "
                    + String.format("%.1f", progress) + "%");
        }
    }
}

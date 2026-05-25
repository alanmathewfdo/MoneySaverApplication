package com.mathew.moneysever.features.user.detail;

import com.mathew.moneysever.data.Repository.MoneyServerDB;
import com.mathew.moneysever.data.dto.SavingGoal;

import java.util.List;

public class DetailModel {

    public SavingGoal addGoal(Long userId, String name, double targetAmount, double savedAmount) {
        return MoneyServerDB.addSavingGoal(userId, name, targetAmount, savedAmount);
    }

    public List<SavingGoal> getGoals(Long userId) {
        return MoneyServerDB.getSavingGoalsForUser(userId);
    }

    public boolean addSavings(Long userId, Long goalId, double amount) {
        SavingGoal goal = MoneyServerDB.findSavingGoal(userId, goalId);
        if (goal == null) {
            return false;
        }
        goal.setSavedAmount(goal.getSavedAmount() + amount);
        return true;
    }
}

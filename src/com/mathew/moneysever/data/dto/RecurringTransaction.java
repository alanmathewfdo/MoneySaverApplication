package com.mathew.moneysever.data.dto;

public class RecurringTransaction {

    private Long id;
    private Long userId;
    private Transaction.TransactionType type;
    private double amount;
    private String category;
    private String description;
    private Frequency frequency;
    private long nextRunDate;
    private boolean active;

    public enum Frequency {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public void setType(Transaction.TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public long getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(long nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

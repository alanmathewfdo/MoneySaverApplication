package com.mathew.moneysever.util;

import java.util.Scanner;

public class ConsoleInput {

    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleInput(){}

    public static Scanner scanner(){
        return SCANNER;
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                double amount = Double.parseDouble(value);
                if (amount > 0) {
                    return amount;
                }
                System.out.println("Amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    public static double readNonNegativeDouble(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                double amount = Double.parseDouble(value);
                if (amount >= 0) {
                    return amount;
                }
                System.out.println("Amount cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}

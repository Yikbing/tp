package budgetflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinanceTracker {

    // Command constants
    public static final String COMMAND_ADD_INCOME = "add category/";
    public static final String COMMAND_LOG_EXPENSE = "log-expense ";
    public static final String COMMAND_LIST_INCOME = "list income";
    public static final String COMMAND_EXIT = "exit";

    // Command prefixes and their lengths (avoiding magic numbers)
    private static final String ADD_COMMAND_PREFIX = "add ";
    private static final int ADD_COMMAND_PREFIX_LENGTH = ADD_COMMAND_PREFIX.length();
    private static final String LOG_EXPENSE_COMMAND_PREFIX = "log-expense ";
    private static final int LOG_EXPENSE_COMMAND_PREFIX_LENGTH =
            LOG_EXPENSE_COMMAND_PREFIX.length();

    // Field prefixes for income and expense commands
    private static final String PREFIX_CATEGORY = "category/";
    private static final String PREFIX_AMOUNT = "amt/";
    private static final String PREFIX_DATE = "d/";
    private static final String PREFIX_DESCRIPTION = "desc/";

    // Instance fields
    private List<Income> incomes;
    private List<Expense> expenses;
    private Scanner scanner;

    public FinanceTracker(Scanner scanner) {
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.scanner = scanner;
    }

    /**
     * Processes the given command input.
     * Delegates to the appropriate method based on the command.
     *
     * @param input the command input from the user
     */
    public void processCommand(String input) {
        if (input.startsWith(COMMAND_ADD_INCOME)) {
            addIncome(input);
        } else if (input.startsWith(COMMAND_LOG_EXPENSE)) {
            logExpense(input);
        } else if (COMMAND_LIST_INCOME.equals(input)) {
            listIncome();
        } else {
            System.out.println("I don't understand that command. Try again.");
        }
    }

    /**
     * Adds income to the finance tracker.
     * Expected format: add category/CATEGORY amt/AMOUNT d/DATE
     * Example: add category/Part-timeJob amt/300.00 d/June12
     *
     * @param input the full command string
     */
    public void addIncome(String input) {
        // Remove the "add " prefix using the constant's length.
        input = input.substring(ADD_COMMAND_PREFIX_LENGTH);

        // Split the input by spaces.
        String[] parts = input.split(" ");

        String category = null;
        Double amount = null;
        String date = null;

        for (String part : parts) {
            if (part.startsWith(PREFIX_CATEGORY)) {
                category = part.substring(PREFIX_CATEGORY.length());
            } else if (part.startsWith(PREFIX_AMOUNT)) {
                try {
                    amount = Double.parseDouble(part.substring(PREFIX_AMOUNT.length()));
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Error: Invalid amount format. Please enter a valid number.");
                    return;
                }
            } else if (part.startsWith(PREFIX_DATE)) {
                date = part.substring(PREFIX_DATE.length());
            }
        }

        if (category == null) {
            System.out.println("Error: Income category is required.");
            return;
        }
        if (amount == null) {
            System.out.println("Error: Income amount is required.");
            return;
        }
        if (date == null) {
            System.out.println("Error: Income date is required.");
            return;
        }

        Income income = new Income(category, amount, date);
        incomes.add(income);
        System.out.println("Income added: " + category + ", Amount: $" +
                String.format("%.2f", amount) + ", Date: " + date);
    }

    /**
     * Logs an expense in the finance tracker.
     * Expected format: log-expense desc/DESCRIPTION amt/AMOUNT d/DATE
     * Example: log-expense desc/LunchAtCafe amt/12.00 d/Feb18
     *
     * @param input the full command string
     */
    public void logExpense(String input) {
        // Remove the "log-expense " prefix.
        input = input.substring(LOG_EXPENSE_COMMAND_PREFIX_LENGTH);

        // Split the input by spaces.
        String[] parts = input.split(" ");

        String description = null;
        Double amount = null;
        String date = null;

        for (String part : parts) {
            if (part.startsWith(PREFIX_DESCRIPTION)) {
                description = part.substring(PREFIX_DESCRIPTION.length());
            } else if (part.startsWith(PREFIX_AMOUNT)) {
                try {
                    amount = Double.parseDouble(part.substring(PREFIX_AMOUNT.length()));
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Error: Invalid amount format. Please enter a valid number.");
                    return;
                }
            } else if (part.startsWith(PREFIX_DATE)) {
                date = part.substring(PREFIX_DATE.length());
            }
        }

        if (description == null) {
            System.out.println("Error: Expense description is required.");
            return;
        }
        if (amount == null) {
            System.out.println("Error: Expense amount is required.");
            return;
        }
        if (date == null) {
            System.out.println("Error: Expense date is required.");
            return;
        }

        Expense expense = new Expense(description, amount, date);
        expenses.add(expense);
        System.out.println("Expense logged: " + description + ", Amount: $" +
                String.format("%.2f", amount) + ", Date: " + date);
    }

    /**
     * Lists all incomes and prints the total sum.
     * Command: list income
     */
    public void listIncome() {
        if (incomes.isEmpty()) {
            System.out.println("No incomes have been added yet.");
            return;
        }

        double totalIncome = 0.0;
        System.out.println("Income Log:");
        for (Income income : incomes) {
            System.out.println(income.getCategory() + " | $" +
                    String.format("%.2f", income.getAmount()) + " | " +
                    income.getDate());
            totalIncome += income.getAmount();
        }
        System.out.println("Total Income: $" + String.format("%.2f", totalIncome));
    }
}


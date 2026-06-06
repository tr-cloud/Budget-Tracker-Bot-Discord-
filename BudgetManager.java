package budgettracker;

import java.util.ArrayList;

public class BudgetManager {
    private double totalIncome = 0;
    private double totalExpenses = 0;
    private ArrayList<String> expensesList = new ArrayList<>();

    public void addIncome(double amount) {
        totalIncome += amount;
    }

    public void addExpense(String name, double amount) {
        expensesList.add(name + ": ₹" + amount);
        totalExpenses += amount;
    }

    public String showExpenses() {
        if (expensesList.isEmpty()) return "No expenses added yet.";
        StringBuilder sb = new StringBuilder("Expenses:\n");
        for (String e : expensesList) {
            sb.append("- ").append(e).append("\n");
        }
        return sb.toString();
    }

    public String calculateBudget() {
        if (totalIncome == 0) return "Add income first!";

        double needs = totalIncome * 0.50;
        double wants = totalIncome * 0.30;
        double savings = totalIncome * 0.20;

        return "📊 *50/30/20 Budget Plan*\n"
                + "Needs (50%): ₹" + needs + "\n"
                + "Wants (30%): ₹" + wants + "\n"
                + "Savings (20%): ₹" + savings + "\n";
    }

    public String savingTip() {
        return "💡 Saving Tip: Try the 24-Hour Rule! Wait one day before buying anything non-essential.";
    }
}

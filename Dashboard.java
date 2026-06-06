package budgettracker;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    BudgetManager manager = new BudgetManager();

    public Dashboard() {
        setTitle("Budget Tracker Dashboard");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Simple Budget Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton budgetBtn = new JButton("Show Budget Plan");
        JTextArea output = new JTextArea();
        output.setEditable(false);

        budgetBtn.addActionListener(e -> output.setText(manager.calculateBudget()));

        add(title, BorderLayout.NORTH);
        add(budgetBtn, BorderLayout.CENTER);
        add(new JScrollPane(output), BorderLayout.SOUTH);

        setVisible(true);
    }
}

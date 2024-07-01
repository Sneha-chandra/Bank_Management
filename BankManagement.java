import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class BankManagement extends Frame implements ActionListener, WindowListener {

    // Text fields and buttons for user interaction
    TextField accountNumberField;
    TextField amountField;
    Button depositButton;
    Button withdrawButton;
    Button checkBalanceButton;
    Button closeButton;
    // Account data (replace with a proper data structure like HashMap)
    HashMap<String, Double> accounts = new HashMap<>();

    public BankManagement() {
        super("Bank Management System");

        // Create layout and components
        setLayout(new FlowLayout());
        //setVisible(true);
        accountNumberField = new TextField(15);
        amountField = new TextField(15);
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");
        checkBalanceButton = new Button("Check Balance");

        // Add action listeners for buttons
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);

        // Add components to the frame
        add(new Label("Account Number:"));
        add(accountNumberField);
        add(new Label("Amount:"));
        add(amountField);
        add(depositButton);
        add(withdrawButton);
        add(checkBalanceButton);
       // Button closeButton = new Button("Close");
       closeButton = new Button("Close");
        closeButton.addActionListener(this);
        add(closeButton);
        addWindowListener(this);
        // Set window size and visibility
        setSize(400, 200);
        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    String accountNumber = accountNumberField.getText();
    double amount = 0.0;

    // If the user clicked the 'Close' button, close the window
    if (e.getSource() == closeButton) {
        dispose();
        return;
    }

    // If the user didn't enter the amount or entered an invalid amount, print an error message
    try {
        amount = Double.parseDouble(amountField.getText());
    } catch (NumberFormatException ex) {
        System.out.println("Invalid amount format. Please enter a number.");
        return;
    }

    if (amount == 0.0) {
        System.out.println("Please enter a valid amount.");
        return;
    }

    if (e.getSource() == depositButton) {
        deposit(accountNumber, amount);
    } else if (e.getSource() == withdrawButton) {
        withdraw(accountNumber, amount);
    } else if (e.getSource() == checkBalanceButton) {
        checkBalance(accountNumber);
    }
}
    @Override
    public void windowClosing(WindowEvent e) {
        // Handle the close button (X) on the frame
        dispose();
    }
    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("Window minimized"); // Example action for minimize
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("Window restored"); // Example action for maximize
    }

    private void deposit(String accountNumber, double amount) {
        if (amount > 0.0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Update the balance
                java.sql.PreparedStatement pstmt = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
                pstmt.setDouble(1, amount);
                pstmt.setString(2, accountNumber);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error updating the account balance: " + e.getMessage());
                e.printStackTrace();
            }

            // Print a success message
            System.out.println("Deposited: $" + amount + " to account: " + accountNumber);
        } else {
            System.out.println("Invalid deposit amount. Amount must be positive.");
        }
    }
        // Print a success message
        System.out.println("Deposited: $" + amount + " to account: " + accountNumber);
    } else {
        System.out.println("Invalid deposit amount. Amount must be positive.");
    }
}
    private void withdraw(String accountNumber, double amount) {
        // Implement withdrawal logic (check balance and update)
        if (accounts.containsKey(accountNumber) && accounts.get(accountNumber) >= amount) {
            accounts.put(accountNumber, accounts.get(accountNumber) - amount);
            System.out.println("Withdrew: $" + amount + " from account: " + accountNumber);
        } else {
            System.out.println("Insufficient funds in account: " + accountNumber);
        }
    }

    private void checkBalance(String accountNumber) {
        // Implement logic to display account balance
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Balance for account " + accountNumber + ": $" + accounts.get(accountNumber));
        } else {
            System.out.println("Account not found: " + accountNumber);
        }
    }

    public static void main(String[] args) {
        new BankManagement();
    }
}


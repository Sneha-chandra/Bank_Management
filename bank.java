import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.swing.*;

public class bank extends Frame implements ActionListener, WindowListener {

  // Text fields and buttons for user interaction
  TextField accountNumberField;
  TextField amountField;
  Button depositButton;
  Button withdrawButton;
  Button checkBalanceButton;
  Button closeButton;
  // Account data (replace with a proper data structure like HashMap)
  HashMap<String, Double> accounts = new HashMap<>();

  public bank() {
    super("Bank Management System");

    // Create layout and components
    setLayout(new FlowLayout());
    setVisible(true);
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
    closeButton = new Button("Close");
    closeButton.addActionListener(this);
    add(closeButton);
    addWindowListener(this);

    // Set window size and visibility
    setSize(400, 200);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    String accountNumber = accountNumberField.getText().trim();

    // Check if the account number is empty
    if (accountNumber.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter an account number.");
      return;
    }

    try {
      double amount = Double.parseDouble(amountField.getText().trim());

      if (actionCommand.equals("Deposit")) {
        deposit(accountNumber, amount);
      } else if (actionCommand.equals("Withdraw")) {
        withdraw(accountNumber, amount);
      } else if (actionCommand.equals("Check Balance")) {
        checkBalance(accountNumber);
      }

    } catch (NumberFormatException e1) {
      JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
    }

    // Reset the text fields
    accountNumberField.setText("");
    amountField.setText("");
  }

  @Override
  public void windowClosing(WindowEvent e) {
    dispose();
  }

  // Implement methods for deposit, withdraw and checkBalance
  private void deposit(String accountNumber, double amount) {
    if (amount > 0.0) {
      accounts.put(accountNumber, accounts.getOrDefault(accountNumber, 0.0) + amount);
      String message = "Deposited: $" + amount + " to account: " + accountNumber;
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, "Invalid deposit amount. Amount must be positive.");
    }
  }

  private void withdraw(String accountNumber, double amount) {
    if (accounts.containsKey(accountNumber) && accounts.get(accountNumber) >= amount) {
      accounts.put(accountNumber, accounts.get(accountNumber) - amount);
      String message = "Withdrew: $" + amount + " from account: " + accountNumber;
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, "Insufficient funds in account: " + accountNumber);
    }
  }

  private void checkBalance(String accountNumber) {
    if (accounts.containsKey(accountNumber)) {
      String message = "Balance for account " + accountNumber + ": $" + accounts.get(accountNumber);
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, "Account not found: " + accountNumber);
    }
  }
/*private void saveDataToCsv() {
    try (PrintWriter writer = new PrintWriter("C:\\Users\\sneha\\OneDrive\\Documents\\java_files\\Bank_Data.cvs")) {
        for (String accountNumber : accounts.keySet()) {
            Double balance = accounts.get(accountNumber);
            String line = accountNumber + "," + balance + "\n";
            writer.write(line);
        }
    } catch (IOException e) {
        System.out.println("Error saving data to the file: " + e.getMessage());
    }
} */

  public static void main(String[] args) {
    new bank();
    //
  }
}

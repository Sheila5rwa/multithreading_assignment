package phase3_callable;

public class BankAccount {
    private final int id;
    private int balance;

    public BankAccount(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    // The balance check and the withdrawal happen inside one synchronized method,
    // so no other thread can change the balance between the check and the update.
    public synchronized boolean withdraw(int amount) {
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }
}
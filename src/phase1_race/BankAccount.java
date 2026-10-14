package phase1_race;

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

    public int getBalance() {
        return balance;
    }

    // NOT synchronized: several threads can read and write balance at the same time (race condition)
    public void deposit(int amount) {
        int current = balance;      // 1. read
        Thread.yield();             // gives other threads a chance to run in between (makes the race easier to see)
        balance = current + amount; // 2. write - may overwrite another thread's update
    }

    public boolean withdraw(int amount) {
        if (balance < amount) {
            return false;
        }
        int current = balance;      // 1. read
        Thread.yield();
        balance = current - amount; // 2. write - may overwrite another thread's update
        return true;
    }
}

package phase2_sync;

public class TransferTask implements Runnable {
    private final BankAccount from;
    private final BankAccount to;
    private final int amount;

    public TransferTask(BankAccount from, BankAccount to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        // Only deposit if the withdrawal went through, so money is never created
        if (from.withdraw(amount)) {
            to.deposit(amount);
        }
    }
}

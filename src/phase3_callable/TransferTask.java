package phase3_callable;

import java.util.concurrent.Callable;

public class TransferTask implements Callable<String> {
    private final BankAccount from;
    private final BankAccount to;
    private final int amount;

    public TransferTask(BankAccount from, BankAccount to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public String call() {
        if (!from.withdraw(amount)) {
            return "FAILED: insufficient funds in account " + from.getId()
                    + " for transfer of " + amount;
        }
        to.deposit(amount);
        return "SUCCESS: " + amount + " from account " + from.getId()
                + " to account " + to.getId();
    }
}
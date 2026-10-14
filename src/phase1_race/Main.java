package phase1_race;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int NUM_ACCOUNTS = 10;
    private static final int INITIAL_BALANCE = 10_000;
    private static final int NUM_TRANSFERS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        List<BankAccount> accounts = new ArrayList<>();
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts.add(new BankAccount(i, INITIAL_BALANCE));
        }

        ExecutorService pool = Executors.newFixedThreadPool(10);
        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_TRANSFERS; i++) {
            int from = rand.nextInt(NUM_ACCOUNTS);
            int to;
            do {
                to = rand.nextInt(NUM_ACCOUNTS);
            } while (to == from); // pick two different accounts

            int amount = rand.nextInt(100) + 1; // 1 to 100
            pool.execute(new TransferTask(accounts.get(from), accounts.get(to), amount));
        }

        // Stop accepting new tasks, then wait for all submitted tasks to finish
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        int total = 0;
        for (BankAccount account : accounts) {
            System.out.println("Account " + account.getId() + ": " + account.getBalance());
            total += account.getBalance();
        }

        System.out.println("-----------------------------------");
        System.out.println("PHASE 1 - NO SYNCHRONIZATION");
        System.out.println("Expected total : " + (NUM_ACCOUNTS * INITIAL_BALANCE));
        System.out.println("Final total    : " + total);
        System.out.println(total == NUM_ACCOUNTS * INITIAL_BALANCE
                ? "Total is correct"
                : "Total is WRONG -> race condition proven (off by " + (total - NUM_ACCOUNTS * INITIAL_BALANCE) + ")");
        System.out.println("Time taken     : " + (endTime - startTime) + " ms");
    }
}

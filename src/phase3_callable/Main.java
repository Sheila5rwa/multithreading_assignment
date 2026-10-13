package phase3_callable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        List<Future<String>> results = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_TRANSFERS; i++) {
            int from = rand.nextInt(NUM_ACCOUNTS);
            int to;
            do {
                to = rand.nextInt(NUM_ACCOUNTS);
            } while (to == from); // pick two different accounts

            int amount = rand.nextInt(100) + 1; // 1 to 100
            TransferTask task = new TransferTask(accounts.get(from), accounts.get(to), amount);
            Future<String> future = pool.submit(task);
            results.add(future);
        }

        // Harvest the futures: get() waits for each task and returns its message
        int successes = 0;
        int failures = 0;
        for (Future<String> future : results) {
            try {
                String result = future.get();
                if (result.startsWith("SUCCESS")) {
                    successes++;
                } else {
                    failures++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread interrupted: " + e.getMessage());
                failures++;
            } catch (ExecutionException e) {
                System.out.println("Task threw an exception: " + e.getCause());
                failures++;
            }
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        int total = 0;
        for (BankAccount account : accounts) {
            System.out.println("Account " + account.getId() + ": " + account.getBalance());
            total += account.getBalance();
        }

        System.out.println("-----------------------------------");
        System.out.println("Expected total : " + (NUM_ACCOUNTS * INITIAL_BALANCE));
        System.out.println("Final total    : " + total);
        System.out.println("Successful     : " + successes);
        System.out.println("Failed         : " + failures);
        System.out.println("Tasks counted  : " + (successes + failures));
        System.out.println("Time taken     : " + (endTime - startTime) + " ms");
    }
}
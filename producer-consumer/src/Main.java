import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstrates the producer-consumer pattern with multiple threads.
 * Uses a shared buffer with 2 producers and 3 consumers.
 */
public class Main {
    public static void main(String[] args) {
        // Create shared buffer with capacity of 5 items
        Buffer sharedBuffer = new Buffer(5);
        int totalItems = 60;

        // Thread pool with 5 threads (2 producers + 3 consumers)
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Start 2 producers, each producing 30 items
        executorService.execute(new Producer(totalItems/2, sharedBuffer));
        executorService.execute(new Producer(totalItems/2, sharedBuffer));

        // Start 3 consumers, each consuming 20 items
        executorService.execute(new Consumer(sharedBuffer, totalItems/3));
        executorService.execute(new Consumer(sharedBuffer, totalItems/3));
        executorService.execute(new Consumer(sharedBuffer, totalItems/3));

        // Shutdown executor after all tasks complete
        executorService.shutdown();
    }
}
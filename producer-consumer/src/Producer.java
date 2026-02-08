/**
 * Producer thread that generates items and adds them to the shared buffer.
 */
public class Producer implements Runnable {
    private final int productionCount;
    private final Buffer buffer;

    /**
     * Creates a producer that will generate a specified number of items.
     * @param productionCount Number of items this producer will create
     * @param buffer Shared buffer to produce items into
     */
    public Producer(int productionCount, Buffer buffer) {
        this.productionCount = productionCount;
        this.buffer = buffer;
    }

    /**
     * Produces items in a loop until productionCount is reached.
     * Handles interruption gracefully by preserving interrupt status.
     */
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        for (int i = 0; i < productionCount; i++) {
            try {
                // Add item to shared buffer (may block if buffer is full)
                buffer.produce(i);

                System.out.println("[ Producer ] " + threadName + " produced item: " + i);

                // Simulate production time
                Thread.sleep(50);

            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted. Shutting down...");
                // Preserve interrupted status for proper thread cleanup
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(threadName + " has finished all production.");
    }
}
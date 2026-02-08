/**
 * Consumer thread that removes items from the shared buffer.
 */
public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int productionCount;

    /**
     * Creates a consumer that will consume a specified number of items.
     * @param buffer Shared buffer to consume items from
     * @param productionCount Number of items this consumer will process
     */
    public Consumer(Buffer buffer, int productionCount) {
        this.buffer = buffer;
        this.productionCount = productionCount;
    }

    /**
     * Consumes items in a loop until productionCount is reached.
     * Handles interruption gracefully by preserving interrupt status.
     */
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        for (int i = 0; i < productionCount; i++) {
            try{
                // Remove item from shared buffer (may block if buffer is empty)
                int val = buffer.consume();

                System.out.println("[ Consumer ] " + threadName + " consumed " + val);

                // Simulate consumption time
                Thread.sleep(100);

            }catch (InterruptedException e){
                System.out.println(threadName + " was interrupted. Shutting down...");

                // Preserve interrupted status for proper thread cleanup
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(threadName + "has completed consumption");
    }
}

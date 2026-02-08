/**
 * Thread-safe circular buffer for producer-consumer synchronization.
 * Uses monitor pattern with synchronized methods and wait/notify.
 */
public class Buffer {
    private final int[] buffer;
    private final int capacity;
    private int count = 0; // Tracks actual items in buffer
    private int in = 0;    // Index where next item will be produced
    private int out = 0;   // Index where next item will be consumed
    public boolean isCompleted;

    /**
     * Creates a buffer with specified capacity.
     * @param capacity Maximum number of items the buffer can hold
     */
    public Buffer(int capacity) {
        this.buffer = new int[capacity];
        this.capacity = capacity;
    }

    /**
     * Adds an item to the buffer. Blocks if buffer is full.
     * @param value The item to produce
     * @throws InterruptedException if thread is interrupted while waiting
     */
    public void produce(int value) throws InterruptedException {
        synchronized (this) {
            // Wait while buffer is full (use while to handle spurious wakeups)
            while (count == capacity) {
                System.out.println("Buffer full. Producer is waiting...");
                wait();
            }

            // Add item to buffer at 'in' position
            buffer[in] = value;
            in = (in + 1) % capacity; // Circular buffer: wrap to 0 when reaching end
            count++;

            System.out.println("Produced: " + value + " | Buffer size: " + count);
            notifyAll(); // Wake up waiting consumers
        }
    }

    /**
     * Removes and returns an item from the buffer. Blocks if buffer is empty.
     * @return The consumed item
     * @throws InterruptedException if thread is interrupted while waiting
     */
    public int consume() throws InterruptedException {
        synchronized (this) {
            // Wait while buffer is empty (use while to handle spurious wakeups)
            while (count == 0) {
                System.out.println("Buffer empty. Consumer is waiting...");
                wait();
            }

            // Remove item from buffer at 'out' position
            int value = buffer[out];
            out = (out + 1) % capacity; // Circular buffer: wrap to 0 when reaching end
            count--;

            System.out.println("Consumed: " + value + " | Buffer size: " + count);
            notifyAll(); // Wake up waiting producers
            return value;
        }
    }
}
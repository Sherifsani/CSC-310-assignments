import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FleetSimulation {

    public static void main(String[] args) {
        // 1. Set up the initial fleet data
        Map<String, MutablePoint> initialLocations = new HashMap<>();
        initialLocations.put("Cab-1", new MutablePoint(0, 0));
        initialLocations.put("Cab-2", new MutablePoint(10, 10));
        initialLocations.put("Police-1", new MutablePoint(50, 50));

        // 2. Create the Thread-Safe Tracker
        MonitorVehicleTracker tracker = new MonitorVehicleTracker(initialLocations);

        // 3. Create the View Thread (Reads data)
        Thread viewThread = new Thread(new ViewTask(tracker));
        viewThread.setName("View-Thread");

        // 4. Create Updater Threads (Write data)
        // We create one updater for each vehicle to simulate independent GPS signals
        Thread updater1 = new Thread(new UpdaterTask(tracker, "Cab-1"));
        updater1.setName("Updater-Cab-1");

        Thread updater2 = new Thread(new UpdaterTask(tracker, "Cab-2"));
        updater2.setName("Updater-Cab-2");

        // 5. Start the simulation
        System.out.println("Starting Fleet Simulation...");
        viewThread.start();
        updater1.start();
        updater2.start();
    }

    // --- Task Definitions ---

    // Simulates a GPS updating a vehicle's location
    static class UpdaterTask implements Runnable {
        private final MonitorVehicleTracker tracker;
        private final String vehicleId;
        private final Random random = new Random();

        public UpdaterTask(MonitorVehicleTracker tracker, String vehicleId) {
            this.tracker = tracker;
            this.vehicleId = vehicleId;
        }

        public void run() {
            try {
                while (true) {
                    // Generate random movement
                    int newX = random.nextInt(100);
                    int newY = random.nextInt(100);

                    // Update the tracker (Synchronized access)
                    tracker.setLocation(vehicleId, newX, newY);

                    // Sleep to simulate time between GPS updates
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Simulates a Display rendering the fleet positions
    static class ViewTask implements Runnable {
        private final MonitorVehicleTracker tracker;

        public ViewTask(MonitorVehicleTracker tracker) {
            this.tracker = tracker;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Fetch a consistent snapshot (Deep Copy)
                    Map<String, MutablePoint> snapshot = tracker.getLocations();

                    // Render the snapshot
                    System.out.println("\n--- [View Thread] Fleet Snapshot ---");
                    for (String id : snapshot.keySet()) {
                        MutablePoint point = snapshot.get(id);
                        System.out.println(id + ": (" + point.getX() + ", " + point.getY() + ")");
                    }
                    System.out.println("------------------------------------");

                    // Refresh rate (e.g., every 1 second)
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
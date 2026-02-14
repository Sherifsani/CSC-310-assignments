# Vehicle Fleet Tracker

A thread-safe vehicle fleet tracking system demonstrating concurrent programming concepts in Java. This project simulates real-time GPS tracking of vehicles with multiple threads updating and viewing vehicle locations simultaneously.

## 📋 Overview

The Vehicle Fleet Tracker simulates a real-world fleet management system where:
- Multiple vehicles send GPS location updates concurrently
- A monitoring system displays real-time vehicle positions
- Thread safety is ensured through proper synchronization
- Data consistency is maintained via deep copying

## 🏗️ Architecture

The project consists of three main components:

### 1. **MutablePoint**
A simple mutable 2D coordinate class representing vehicle locations with x and y coordinates.

### 2. **MonitorVehicleTracker**
The core thread-safe tracker that:
- Stores vehicle locations in a synchronized map
- Provides thread-safe methods for updating and retrieving locations
- Implements deep copying to prevent data races
- Returns immutable snapshots of the fleet state

### 3. **FleetSimulation**
The main simulation driver that:
- Initializes the fleet with sample vehicles
- Creates multiple updater threads (simulating GPS updates)
- Creates a view thread (simulating the monitoring dashboard)
- Demonstrates concurrent read/write operations

## 🔑 Key Features

- **Thread Safety**: All shared data access is properly synchronized
- **Data Consistency**: Deep copying ensures snapshot integrity
- **Concurrent Updates**: Multiple vehicles can update locations simultaneously
- **Real-time Monitoring**: View thread displays fleet positions every second
- **Scalable Design**: Easy to add more vehicles or monitoring threads

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- A Java IDE (IntelliJ IDEA, Eclipse, etc.) or command-line tools

### Compilation

```bash
cd src
javac *.java
```

### Running the Simulation

```bash
java FleetSimulation
```

## 📊 Sample Output

```
Starting Fleet Simulation...

--- [View Thread] Fleet Snapshot ---
Cab-1: (42, 73)
Cab-2: (18, 91)
Police-1: (50, 50)
------------------------------------

--- [View Thread] Fleet Snapshot ---
Cab-1: (67, 22)
Cab-2: (35, 44)
Police-1: (50, 50)
------------------------------------
```

## 🔧 Configuration

You can customize the simulation by modifying `FleetSimulation.java`:

- **Add more vehicles**: Add entries to `initialLocations` map
- **Change update frequency**: Modify `Thread.sleep(500)` in `UpdaterTask`
- **Change view refresh rate**: Modify `Thread.sleep(1000)` in `ViewTask`
- **Adjust coordinate range**: Change `random.nextInt(100)` for different ranges

## 🧵 Concurrency Concepts Demonstrated

1. **Monitor Pattern**: Using synchronized methods for mutual exclusion
2. **Deep Copying**: Creating independent copies to avoid data races
3. **Immutable Returns**: Returning unmodifiable maps to prevent external modifications
4. **Thread Safety**: Protecting shared mutable state from concurrent access
5. **Producer-Consumer Pattern**: Updater threads produce data, view thread consumes it

## 📝 Class Details

### MonitorVehicleTracker

**Key Methods:**
- `setLocation(String id, int x, int y)`: Updates a vehicle's location (synchronized)
- `getLocations()`: Returns a deep copy of all vehicle locations (synchronized)
- `getLocation(String id)`: Returns a copy of a specific vehicle's location (synchronized)
- `deepCopy(Map<String, MutablePoint> m)`: Creates an independent copy of the location map

### FleetSimulation

**Inner Classes:**
- `UpdaterTask`: Runnable that simulates GPS updates for a vehicle
- `ViewTask`: Runnable that periodically displays the fleet snapshot

## 🎓 Learning Objectives

This project demonstrates:
- How to design thread-safe data structures
- The importance of synchronization in concurrent systems
- Trade-offs between performance and consistency
- Practical application of Java concurrency primitives
- Best practices for sharing mutable state between threads

## ⚠️ Design Considerations

- **Synchronization Overhead**: All methods are synchronized, which may impact performance with many threads
- **Deep Copying Cost**: Creating full copies for each read operation is safe but potentially expensive
- **Liveness**: The current design avoids deadlocks by using a single monitor
- **Scalability**: For production systems, consider using `ConcurrentHashMap` or read-write locks

## 🔮 Future Enhancements

- Add vehicle addition/removal at runtime
- Implement distance calculations between vehicles
- Add geofencing and alert capabilities
- Persist location history to a database
- Create a GUI dashboard for visualization
- Implement RESTful API for remote monitoring

## 📄 License

This project is part of CSC-310 coursework.

## 👤 Author

Created for CSC-310 Assignments

---

**Note**: This is an educational project demonstrating concurrent programming concepts in Java.


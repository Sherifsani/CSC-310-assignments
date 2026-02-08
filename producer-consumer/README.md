# Producer-Consumer Problem

A Java implementation of the classic producer-consumer synchronization problem using threads and a shared circular buffer.

## Overview

This program demonstrates concurrent programming concepts with multiple producer and consumer threads sharing a bounded buffer. It uses Java's built-in synchronization mechanisms (`synchronized`, `wait()`, `notifyAll()`) to coordinate access.

## Components

- **Buffer.java**: Thread-safe circular buffer with blocking operations
- **Producer.java**: Thread that generates items and adds them to the buffer
- **Consumer.java**: Thread that removes and processes items from the buffer
- **Main.java**: Entry point that creates and manages producer/consumer threads

## How It Works

1. A shared buffer with capacity of 5 items is created
2. Two producer threads each produce 30 items (60 total)
3. Three consumer threads each consume 20 items (60 total)
4. Producers block when buffer is full
5. Consumers block when buffer is empty
6. Synchronization ensures thread-safe access

## Running the Program

```bash
# Compile
javac src/*.java

# Run
java -cp src Main
```

## Key Concepts

- **Monitor Pattern**: Uses `synchronized` methods for mutual exclusion
- **Circular Buffer**: Efficient fixed-size buffer with wraparound indexing
- **Wait/Notify**: Threads wait when buffer is full/empty and notify when state changes
- **Thread Pool**: Uses `ExecutorService` for managing multiple threads

## Output

The program prints real-time status of production and consumption:
- When items are produced/consumed
- Current buffer size
- When threads are waiting (buffer full/empty)
- When threads complete their work

# IS2101-InterruptControllerSim--NNM24IS216-

This Java program simulates interrupt handling from multiple I/O devices using **multithreading**.

# Features
1. Simulates Keyboard, Mouse, and Printer interrupts
2. Implements priority-based handling:
3. Keyboard > Mouse > Printer
4. Supports masking/unmasking of specific devices during runtime
5. Logs ISR (Interrupt Service Routine) execution with timestamps
Produces clear, console-based simulation output

## How to Run
```bash
javac InterruptSim.java
java InterruptSim
```

# Output
<img width="432" height="770" alt="Screenshot 2025-10-27 212328" src="https://github.com/user-attachments/assets/75eed145-e6f7-416a-8e53-3d0004fb0a66" />
<img width="407" height="614" alt="Screenshot 2025-10-27 212341" src="https://github.com/user-attachments/assets/880f909e-3816-4fc9-99ba-0b5fcf788d54" />


# Conclusion

This project successfully demonstrates an Interrupt Controller Simulation in Java using multithreading.
Handles multiple device interrupts with priority-based scheduling.
Supports masking/unmasking during runtime.
Provides real-time ISR logging with timestamps.
This simulation helps in understanding how interrupts, priorities, masking, and concurrent execution work in operating systems and embedded systems.
This program helps in understanding the concepts of **interrupts, priority handling, masking, and multithreading** in real-world systems.


# IS2101-InterruptControllerSim--NNM24IS216-

This C program simulates interrupt handling from multiple I/O devices using **pthreads**.

## Features
- Keyboard, Mouse, Printer interrupts
- Priority handling: Keyboard > Mouse > Printer
- Supports masking/unmasking during runtime
- ISR handling with timestamp logging

## How to Run
```bash
gcc interrupt_simulation.c -o interrupt_simulation -lpthread
./interrupt_simulation
```

# Output
![WhatsApp Image 2025-10-24 at 6 42 55 PM](https://github.com/user-attachments/assets/7b82a8b1-d64a-4b22-8c04-a5f06e048b97)

# Conclusion

This project successfully demonstrates an **Interrupt Controller simulation** using C and pthreads.  
- It handles multiple device interrupts with **priority** (Keyboard > Mouse > Printer).  
- It supports **masking/unmasking** during runtime.  
- The simulation provides clear output showing ISR handling and ignored interrupts.  

This program helps in understanding the concepts of **interrupts, priority handling, masking, and multithreading** in real-world systems.


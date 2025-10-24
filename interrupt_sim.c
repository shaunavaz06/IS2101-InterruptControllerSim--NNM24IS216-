#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

#define NUM_DEVICES 3
#define KEYBOARD_PRIORITY 1
#define MOUSE_PRIORITY 2
#define PRINTER_PRIORITY 3
#define MAX_INTERRUPTS 3  // each device will trigger 3 times

typedef struct {
    char name[20];
    int priority;
    bool masked;
} Device;

pthread_mutex_t lock;
Device devices[NUM_DEVICES];
bool running = true;

void get_timestamp(char *buffer) {
    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    strftime(buffer, 30, "%Y-%m-%d %H:%M:%S", t);
}

void handle_interrupt(Device *dev) {
    char time_str[30];
    get_timestamp(time_str);
    printf("%s Interrupt Triggered → Handling ISR → Completed at %s\n",
           dev->name, time_str);
}

void *device_thread(void *arg) {
    Device *dev = (Device *)arg;

    for (int count = 0; count < MAX_INTERRUPTS; count++) {
        sleep(rand() % 3 + 1);
        pthread_mutex_lock(&lock);

        if (!dev->masked) {
            handle_interrupt(dev);
        }

        pthread_mutex_unlock(&lock);
    }

    return NULL;
}

int main() {
    srand(time(NULL));
    pthread_t threads[NUM_DEVICES];
    pthread_mutex_init(&lock, NULL);

    devices[0] = (Device){"Keyboard", KEYBOARD_PRIORITY, false};
    devices[1] = (Device){"Mouse", MOUSE_PRIORITY, false};
    devices[2] = (Device){"Printer", PRINTER_PRIORITY, false};

    for (int i = 0; i < NUM_DEVICES; i++) {
        pthread_create(&threads[i], NULL, device_thread, &devices[i]);
    }

    for (int i = 0; i < NUM_DEVICES; i++) {
        pthread_join(threads[i], NULL);
    }

    pthread_mutex_destroy(&lock);
    printf("\n--- Simulation ended successfully ---\n");
    return 0;
}
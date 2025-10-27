import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InterruptSim {
    private volatile boolean[] pending = new boolean[3];
    private volatile boolean[] masked  = new boolean[3];
    private final List<String> isrLog = new ArrayList<>();
    private final SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
    private volatile boolean running = true;
    private Random rand = new Random();

    public static void main(String[] args) throws Exception {
        new InterruptSim().startSimulation();
    }

    private void startSimulation() throws Exception {
        masked[0] = false;
        masked[1] = false;
        masked[2] = true;

        System.out.println("Keyboard enabled");
        System.out.println("Mouse enabled");
        System.out.println("Printer masked");
        System.out.println();

        Thread keyboardThread = deviceThread(0, "Keyboard", 700, 1400);
        Thread mouseThread    = deviceThread(1, "Mouse",    900, 1800);
        Thread printerThread  = deviceThread(2, "Printer", 1000, 2500);

        keyboardThread.start();
        mouseThread.start();
        printerThread.start();

        long simDurationMs = 12000L;
        long simStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - simStart < simDurationMs) {
            boolean handledSomething = false;

            for (int pri = 0; pri < 3; pri++) {
                if (pending[pri]) {
                    if (masked[pri]) {
                        printLeftMasked(pri);
                        pending[pri] = false;
                        handledSomething = true;
                        sleepMillis(200);
                    } else {
                        handleISR(pri);
                        handledSomething = true;
                    }
                    break;
                }
            }

            if (!handledSomething) {
                sleepMillis(150);
            }
        }

        running = false;
        keyboardThread.join();
        mouseThread.join();
        printerThread.join();

        System.out.println();
        System.out.println("=== ISR Log ===");
        for (String entry : isrLog) {
            System.out.println(entry);
        }
        System.out.println();
        System.out.println("Simulation complete.");
        System.out.println();
        System.out.println("=== Code Execution Successful ===");
    }

    private Thread deviceThread(int deviceId, String name, int minDelay, int maxDelay) {
        return new Thread(() -> {
            try {
                while (running) {
                    int wait = rand.nextInt(Math.max(1, maxDelay - minDelay + 1)) + minDelay;
                    sleepMillis(wait);
                    pending[deviceId] = true;
                }
            } catch (Exception e) {}
        }, "Device-" + name);
    }

    private void handleISR(int deviceId) {
        String name = deviceName(deviceId);
        System.out.println(name + " ? ISR done");
        String timestamp = timeFmt.format(new Date());
        String logEntry = timestamp + " - " + name;
        synchronized (isrLog) {
            isrLog.add(logEntry);
        }
        int processing = 300 + deviceId * 150;
        sleepMillis(processing);
        pending[deviceId] = false;
        sleepMillis(120);
    }

    private void printLeftMasked(int deviceId) {
        String name = deviceName(deviceId);
        System.out.println(name + " masked");
    }

    private String deviceName(int id) {
        switch (id) {
            case 0: return "Keyboard";
            case 1: return "Mouse";
            default: return "Printer";
        }
    }

    private void sleepMillis(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}

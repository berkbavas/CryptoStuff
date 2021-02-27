package util;

public class Stopwatch {
    private long start, elapsed;
    private boolean running, stopped;

    public Stopwatch() {
        running = false;
        stopped = true;
        elapsed = 0;
        start = 0;
    }

    public void start() {
        start = System.nanoTime();
        elapsed = 0;
        running = true;
        stopped = false;
    }

    public void suspend() {
        if (running) {
            elapsed += System.nanoTime() - start;
            running = false;
        }
    }

    public void resume() {
        if (running)
            return;

        if (stopped)
            return;

        start = System.nanoTime();
        running = true;
    }

    public void stop() {
        if (running) {
            elapsed += System.nanoTime() - start;
        }

        running = false;
        stopped = true;
    }

    public long getCurrentElapsed() {
        if (running)
            return elapsed + System.nanoTime() - start;
        else
            return elapsed;
    }

    public void print() {
        System.out.println(this);
    }

    public String toString() {
        long ms = getCurrentElapsed() / 1000000;
        return "Elapsed time is " + ms + " ms.";
    }

}

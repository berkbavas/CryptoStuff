package com.github.berkbavas.crypto.util;

public class Stopwatch {
    private static final double NS_TO_MS = 1.0E-06;
    private long start, elapsed;
    private boolean running, stopped;
    private final boolean precise;

    public Stopwatch() {
        precise = false;
        start();
    }

    public Stopwatch(boolean precise) {
        this.precise = precise;
        start();
    }

    public void start() {
        start = time();
        elapsed = 0;
        running = true;
        stopped = false;
    }

    public long suspend() {
        if (running) {
            elapsed += time() - start;
            running = false;
        }

        return elapsed;
    }

    public void resume() {
        if (running)
            return;

        if (stopped)
            return;

        start = time();
        running = true;
    }

    public long stop() {
        if (running) {
            elapsed += time() - start;
        }

        running = false;
        stopped = true;

        return elapsed;
    }

    public long elapsed() {
        if (running)
            return elapsed + time() - start;
        else
            return elapsed;
    }

    public void print() {
        System.out.println(this);
    }

    public String toString() {
        return String.format("%d %s", elapsed(), precise ? "ns" : "ms");
    }

    private long time() {
        if (precise)
            return System.nanoTime();
        else
            return (long) (System.nanoTime() * NS_TO_MS);
    }

}

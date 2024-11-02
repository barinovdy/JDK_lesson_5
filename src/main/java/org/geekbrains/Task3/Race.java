package org.geekbrains.Task3;

import java.util.concurrent.CountDownLatch;

public class Race implements Runnable{
    private CountDownLatch readySignal;
    private CountDownLatch startSignal;
    private CountDownLatch finishSignal;

    public Race(CountDownLatch readySignal, CountDownLatch startSignal, CountDownLatch finishSignal) {
        this.readySignal = readySignal;
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
    }

    @Override
    public void run() {
        try {
            readySignal.await();
            System.out.println("На старт!");
            Thread.sleep(100);
            System.out.println("Внимание!");
            Thread.sleep(100);
            System.out.println("Марш!");
            startSignal.countDown();
            finishSignal.await();
            System.out.println("Забег окончен!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

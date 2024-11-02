package org.geekbrains.Task2;

public class ThreadB implements Runnable{
    private int counter = 100;
    private ThreadA threadA;

    public ThreadB(ThreadA threadA) {
        this.threadA = threadA;
    }

    @Override
    public void run() {
        while(counter >= 0){
            if (threadA.isSwitcher()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(counter--);
            }
        }
    }
}

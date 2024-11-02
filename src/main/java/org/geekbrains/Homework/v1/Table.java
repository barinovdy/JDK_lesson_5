package org.geekbrains.Homework.v1;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Table implements Runnable {
    private static int EAT_MAX_COUNT = 3;

    List<String> philosopherNames;
    Philosopher[] philosophers;
    boolean[] forksAvailable;
    int leftFork;
    int rightFork;
    CountDownLatch philosopherEat;

    public Table(List<String> philosopherNames) {
        this.philosopherNames = philosopherNames;
        philosopherEat = new CountDownLatch(philosopherNames.size());
    }

    private void initPhilosophers(){
        philosophers = new Philosopher[philosopherNames.size()];
        for (int i = 0; i < philosophers.length; i++) {
            if (i < philosophers.length - 1) {
                leftFork = i;
                rightFork = i + 1;
            } else {
                leftFork = philosophers.length - 1;
                rightFork = 0;
            }
            philosophers[i] = new Philosopher(philosopherNames.get(i), this, leftFork, rightFork, philosopherEat, EAT_MAX_COUNT);
        }
        System.out.println("Да начнется заседание с трапезой");
    }

    private void initForks(){
        forksAvailable = new boolean[philosopherNames.size()];
        for (int i = 0; i < philosopherNames.size(); i++) {
            forksAvailable[i] = true;
        }
    }

    public boolean takeForks(int leftFork, int rightFork){
        if (forksAvailable[leftFork] & forksAvailable[rightFork]) {
            forksAvailable[leftFork] = false;
            forksAvailable[rightFork] = false;
            return true;
        }
        return false;
    }

    public void freeForks(int leftFork, int rightFork){
        forksAvailable[leftFork] = true;
        forksAvailable[rightFork] = true;
    }

    @Override
    public void run() {
        initForks();
        initPhilosophers();
        for (int i = 0; i < philosophers.length; i++) {
            new Thread(philosophers[i]).start();
        }
        try {
            philosopherEat.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Все философы поели " + EAT_MAX_COUNT + " раз(а)");
    }
}

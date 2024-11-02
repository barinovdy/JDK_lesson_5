package org.geekbrains.Homework.v1;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
    private static int EAT_MAX_COUNT_PER_TIME = 2;
    private static int MAX_EAT_TIME = 5000;
    private static int MIN_EAT_TIME = 1000;
    private static int MAX_THINK_TIME = 7000;
    private static int MIN_THINK_TIME = 2000;

    private String name;
    Table table;
    int leftFork;
    int rightFork;
    CountDownLatch philosopherEat;
    private int eatMaxCount;

    private int eatCount = 0;
    private int eatCountPerTime = 0;

    public Philosopher(String name, Table table, int leftFork, int rightFork, CountDownLatch philosopherEat, int eatMaxCount) {
        this.name = name;
        this.table = table;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.philosopherEat = philosopherEat;
        this.eatMaxCount = eatMaxCount;
    }

    private boolean takeForks(int leftFork, int rightFork){
        if (table.takeForks(leftFork, rightFork)){
            System.out.println("Философ " + name + " взял вилки " + leftFork + " и " + rightFork);
            return true;
        }
        return false;
    }

    private void freeForks(){
        table.freeForks(leftFork, rightFork);
        System.out.println("Философ " + name + " положил вилки " + leftFork + " и " + rightFork);
    }

    private void think(){
        System.out.println("Философ " + name + " размышляет");
        try {
            Thread.sleep(new Random().nextInt(MIN_THINK_TIME, MAX_THINK_TIME));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat(){
        if (eatCountPerTime < EAT_MAX_COUNT_PER_TIME) {
            //Если поели менее двух раз подряд
            if (takeForks(leftFork, rightFork)){
                //Получилось взять вилки
                try {
                    Thread.sleep(new Random().nextInt(MIN_EAT_TIME, MAX_EAT_TIME));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eatCount++;
                eatCountPerTime++;
                freeForks();
                System.out.println("Философ " + name + " поел " + eatCount + " раз(а)");
            } else {
                think();
            }
        } else {
            think();
            eatCountPerTime = 0;
        }
    }

    @Override
    public void run() {
        while (eatCount < eatMaxCount){
            eat();
        }
        philosopherEat.countDown();
    }
}

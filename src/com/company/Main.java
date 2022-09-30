package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {

    public static void main(String[] args) {

        ExecutorService executor =
               Executors.newSingleThreadExecutor(new MyThreadFactory());
        MyCustomThread myCustomThread = new MyCustomThread();
        myCustomThread.setRandomNumbers(myCustomThread.generateRandomArray());
        System.out.println("Generated random numbers");
        System.out.println(myCustomThread.getRandomNumbers());
        System.out.println("-----------------------------------------");
        for (int i=0; i< 100; i++) {
            executor.submit(myCustomThread);
        }
    }
}

class MyCustomThread implements Runnable {

    private static int count = 0;
    private static ArrayList<Integer> randomNumbers;

    @Override
    public void run() {
        count += 1;
        System.out.println("Before Thread sleep "+ count);
        try {
            if (randomNumbers.contains(count)) {
                System.out.println("Random number selected as " + count + ". Thread Sleep is 5000 now");
                Thread.sleep(5000);
            } else {
                Thread.sleep(1000);
            }
            System.out.println("After Thread sleep "+ count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> generateRandomArray(){
        Random randomNum = new Random();
        ArrayList<Integer> randomArray = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            int randomNo = 1 + randomNum.nextInt(100);
            randomArray.add(randomNo);
        }
        return randomArray;
    }

    public ArrayList<Integer> getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(ArrayList<Integer> generatedRandomNumbers) {
        randomNumbers = generatedRandomNumbers;
    }
}

class MyThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        return new Thread(r, "MyThread");
    }
}

package ru.spbstu.telematics.java;

public class Customer extends Thread
{
    Customer (boolean isGreedyValue, Queue queueValue)
    {
        isGreedy = isGreedyValue;
        queue = queueValue;
        queue.add(this);
    }
    boolean isGreedy = false;
    static int duration = 3000;
    Queue queue;

    public void run()
    {
        if(isGreedy)
        {
            System.out.printf("Жадный в процессе" + "\n");
        }
        else
        {
            System.out.printf("Терпеливый в процессе" + "\n");
        }
        for (int percent = 10; percent <= 100; percent = percent + 10)
        {
            try
            {
                Thread.sleep(duration/10);
                System.out.printf(percent + "%%" + "\n");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.printf("СЫР!" + "\n" + "\n");
        queue.getCheese();
    }
}

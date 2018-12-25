package ru.spbstu.telematics.java;


public class App 
{
    public static void main( String[] args )
    {
        Queue queue = new Queue();

        {
            Customer customer = new Customer(false, queue);
        }
        {
            Customer customer = new Customer(false, queue);
        }

        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        {
            Customer customer = new Customer(true, queue);
        }


        try
        {
            Thread.sleep(8500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        {
            Customer customer = new Customer(false, queue);
        }
        {
            Customer customer = new Customer(false, queue);
        }
        {
            Customer customer = new Customer(true, queue);
        }
    }
}

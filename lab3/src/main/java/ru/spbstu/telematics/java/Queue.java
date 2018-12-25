package ru.spbstu.telematics.java;

import java.util.ArrayList;

public class Queue
{
    void add(Customer customer)
    {

        if (customer.isGreedy == true)
        {
            customerList.add(customer);
            System.out.printf("Жадный добавлен!" + "\n");
        }
        else
        {
            customerList.add(0, customer);
            System.out.printf("Терпеливый добавлен" + "\n");
        }
        if (isFree)
        {
            getCheese();
        }
    }

    void getCheese()
    {
        if (customerList.isEmpty())
        {
            isFree = true;
            return;
        }
        isFree = false;
        customerList.get(customerList.size()-1).start();
        customerList.remove(customerList.size()-1);
    }

    ArrayList<Customer> customerList = new ArrayList<Customer>();
    boolean isFree = true;
}

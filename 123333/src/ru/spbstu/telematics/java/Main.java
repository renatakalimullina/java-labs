package ru.spbstu.telematics.java;

public class Main {

    public static void main(String[] args)
    {
        LinkedHashSet<Integer> ghj = new LinkedHashSet();

        ghj.add(1);

        ghj.add(9);

        ghj.add(4);

        ghj.add(1);

        ghj.print();

        ghj.remove(1);

        ghj.print();

        boolean c = ghj.add(1);

        ghj.print();

        ghj.remove(4);

        ghj.print();

        ghj.add(4);

        ghj.print();


        ghj.getValue(3);

        ghj.getValue(9);

        ghj.getValue(4);

        int y = 7;
    }
}


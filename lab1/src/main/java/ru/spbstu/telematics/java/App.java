package ru.spbstu.telematics.java;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        Random rnd = new Random(System.currentTimeMillis());
        int min = 1, max = 9;
        int N = min + rnd.nextInt(max - min + 1);

        Vector vector1 = new Vector(N);
        Vector vector2 = new Vector(N);

        int finish = vector1.scalar(vector2);

        System.out.println(" \n");
        System.out.println(finish);


    }
}


class Vector {

    private int size;
    private int[] vector;

    Vector(int N) {

        size = N;
        vector = new int[N];

        Random rnd = new Random(System.currentTimeMillis());
        int min = -10, max = 10;
        for (int i = 0; i < N; i++)
            vector[i] = min + rnd.nextInt(max - min + 1);

    }


    Vector(int[] array) {
        size = array.length;
        vector = new int[size];

        for (int i = 0; i < size; i++)
            vector[i] = array[i];
    }


    int scalar(Vector vector1) {

        int scalar = 0;
        for (int i = 0; i < size; i++)
            scalar += vector1.vector[i] * this.vector[i];

        return scalar;
    }

}

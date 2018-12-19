package ru.spbstu.telematics.java;

public interface Collection <T>{

    int size();
    boolean contains(T value);
    boolean add(T value);
    boolean remove(T value);
    int getKey(T value);
    T getValue(int key);
}

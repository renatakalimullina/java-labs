package ru.spbstu.telematics.java;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public interface Iterable<T> {

    default void forEach(Consumer<? super T> action)  //Выполняет данное действие для каждого элемента Iterable до тех пор,
    // пока все элементы не будут обработаны, или действие не выдает исключение
    {

        LinkedHashSet a;
    }
    Iterator<T> iterator();  //Возвращает итератор над элементами типа T.
}

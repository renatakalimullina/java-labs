package ru.spbstu.telematics.java;

import java.util.HashMap;
import java.util.Iterator;

public class LinkedHashSet<T> implements Collection<T>, Iterable<T>
{
    private HashMap<Integer, Cell> map = new HashMap();  //список
    private int size;

    private T first;  //для того, чтобы запомнить первый элемент, который добавили
    private Cell lastPtr = new Cell(); //для запоминания последнего добавленного элемента

    LinkedHashSet() { size = 0; }


    public boolean add(T val)
    {
        if(map.containsKey(val.hashCode()))  //проверка, есть ли такой хэш-код уже в коллекции
        {
            return false;
        }
        else
        {
            Cell cellHere = new Cell();
            cellHere.value = val;
            cellHere.next =  null;  //останется next!!!
            cellHere.last = null;

            if(size != 0)
            {
                lastPtr.next = cellHere;  //доработали с предыдущим элементом(забили все поля)
                cellHere.last = lastPtr;  //останется last!!!

                lastPtr = cellHere;  //"предыдущий" становится нынешний(3-ий становится прошлым)
            }
            else
            {
                first = val;

                lastPtr = cellHere;  //"предыдущий" становится нынешний(3-ий становится прошлым)
            }

            map.put(val.hashCode(), cellHere);
            size++;

            return true;
        }
    }


    public int size()
    {
        return size;
    }


    public boolean contains(T value)
    {
        if(map.containsKey(value.hashCode()))
            return true;
        else
            return false;
    }



    public boolean remove(T value)
    {
        if(!map.containsKey(value.hashCode()))
            return false;
        else
        {
            int hashCode = value.hashCode();

            Cell lastCell = new Cell();  //ссылка предыдущий элемент
            lastCell = map.get(hashCode).last;
            Cell nextCell = new Cell();   //ссылка следующий элемент
            nextCell = map.get(hashCode).next;


            if(value != first)
            {
                lastCell.next = nextCell;
                nextCell.last = lastCell;
            }
            else
            {

                first = (T) nextCell.value;
                nextCell.last = null;
                nextCell = null;
            }

            map.remove(hashCode);
            size--;
           return true;
        }
    }



    public int getKey(T value)
    {
        return  value.hashCode();
    }

    public T getValue(int key)
    {
        T ptr = first;
        int keyFirst = first.hashCode();
        for(int i = 0; i < size - 1; i++)
        {
            if( keyFirst == key)
                return ptr;
            else
            {
                Cell nextCell = new Cell();   //ссылка следующий элемент
                nextCell = map.get(keyFirst).next;

                keyFirst = nextCell.value.hashCode();
                ptr = (T) nextCell.value;
            }
        }
        if(keyFirst  == key)
            return ptr;

        return null;
        //return (T) map.get(key);
    }


    public void dereference(T Last) //разыменовать
    {

        int hashCodeLast = Last.hashCode();

        Cell lastCell = new Cell();
        lastCell = map.get(hashCodeLast);

        Cell now = new Cell();
        now = lastCell.next;

        if(now != null)
        {
            T result  = (T) now.value;

            System.out.println(result);

            dereference(result);
        }
        else
            return;
    }


    public void print()
    {
        System.out.println(first);

        dereference(first);

        return;
    }









    public Iterator<T> iterator() {
        return null;
    }
}

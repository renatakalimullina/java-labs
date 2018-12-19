package ru.spbstu.telematics.java;

public class Main {

    public static void main(String[] args)
    {
        MyLinkedHashSet<Integer> ghj = new MyLinkedHashSet<>();
       //Set<Integer> ghj = new LinkedHashSet<>();
        ghj.add(1);
        ghj.add(9);
        ghj.add(4);

       // ghj.print();


        for(Integer i: ghj) {
            System.out.println(i);
        }

        var it = ghj.iterator();
        it.next();

        boolean c = ghj.add(1);  //должно быть false

        ghj.remove(4);
        ghj.remove(9);
        ghj.remove(1);

        ghj.remove(1);  //вернет false потому что нет такого элемента

        int v = ghj.size();  // =0

        ghj.add(1);
        ghj.add(4);
        ghj.add(9);
//
//        ghj.print();
//
//        ghj.remove(4);
//
//        ghj.print();
//
//        ghj.add(4);
//
//        ghj.print();
//
//
//        int i =  ghj.getValue(9);
//
//        i = ghj.getKey(4);

    }
}

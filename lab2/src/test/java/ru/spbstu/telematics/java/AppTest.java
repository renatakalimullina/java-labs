package ru.spbstu.telematics.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    public void test1()
    {
        MyLinkedHashSet<Integer> example = new MyLinkedHashSet();

        example.add(5);

        assertEquals(example.add(5), false);
    }

    public void test2()
    {
        MyLinkedHashSet<Integer> example = new MyLinkedHashSet();

        example.add(5);

        assertEquals(example.remove(8), false);
    }

    public void test3()
    {
        MyLinkedHashSet<Integer> example = new MyLinkedHashSet();

        example.add(5);
        example.add(4);
        example.add(3);

        assertEquals(example.size(), 3);
    }


    public void test4()
    {
        MyLinkedHashSet<Integer> example = new MyLinkedHashSet();

        example.add(5);
        example.add(4);
        example.add(3);

        assertEquals(example.contains(2), false);
    }


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}

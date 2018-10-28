package ru.spbstu.telematics.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{

    public void testScalar(){

        int[] testArray_1 = {1, 2, 3};
        int[] testArray_2 = {3, 4, 5};


        Vector A = new Vector(testArray_1);
        Vector B = new Vector(testArray_2);


        assertEquals(A.scalarMultiplication(B), 26);
    }


    public void testScalarOrthogonality(){

        int[] testArray_1 = {1, 2};
        int[] testArray_2 = {2, -1};


        Vector A = new Vector(testArray_1);
        Vector B = new Vector(testArray_2);

        assertEquals(A.scalarMultiplication(B), 0);
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

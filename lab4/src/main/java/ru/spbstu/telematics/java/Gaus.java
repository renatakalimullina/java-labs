package ru.spbstu.telematics.java;

public class Gaus
{
    private static double[][] A;
    private static double[] B;
    private static double[] X;

    private final static int N = 2000;
    private final static int threadsCounter = 64;

    static class Barrier
    {
        private int awaitingThreads;

        Barrier()
        {
            awaitingThreads = threadsCounter;
        }

        synchronized void await() throws InterruptedException
        {
            awaitingThreads--;

            if (awaitingThreads > 0)
            {
                this.wait();
            }
            else
            {
                awaitingThreads = threadsCounter;
                notifyAll();
            }
        }
    }

    private static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(A[i][j] + " ");
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    private static void checkResult()
    {
        double[] r = new double[N];
        for (int i = 0; i < N; i++)
        {
            r[i] = 0;
            for (int j = 0; j < N; j++)
                r[i] += A[i][j] * X[j];
            r[i] -= B[i];
        }
        double result = 0;
        for (int i = 0; i < N; i++)
            result += r[i] * r[i];
        result = Math.sqrt(result);
        System.out.println("Error is " + result + ".\n");
    }

    private static class MyThread implements Runnable
    {
        int ID;
        Barrier b;

        MyThread(int id, Barrier barrier)
        {
            ID = id;
            b = barrier;
        }

        public void run() {
            for (int row = 0; row < N; row++)
            {
                try
                {
                    b.await();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if (ID == 0)
                    partialPivoting(row);  //Поиск опорного элемента

                try
                {
                    b.await();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                double tf = row + ID * (N - row) / (float) threadsCounter;
                int thread_row = (int) tf;

                for (int k = thread_row; k < Math.round(tf + (N - row) / (double) threadsCounter); k++)
                {
                    if (k <= row)
                        continue;
                    double m = A[k][row] / A[row][row];
                    for (int col = row; col < N; col++)
                    {
                        A[k][col] -= m * A[row][col];
                    }
                    B[k] -= m * B[row];
                }
            }
        }
    }

    private static void partialPivoting(int row)  //Поиск опорного элемента
    {
        double ksave = -1.0f;
        int k_i = 0;

        for (int col = row; col < N; col++)
        {
            if (Math.abs(A[col][row]) > ksave)
            {
                ksave = Math.abs(A[col][row]);
                k_i = col;
            }
        }

        for (int col = row; col < N; col++)
        {
            double tmp = A[k_i][col];
            A[k_i][col] = A[row][col];
            A[row][col] = tmp;
        }
        double tmp = B[k_i];
        B[k_i] = B[row];
        B[row] = tmp;
    }

    public static void main(String[] args) throws InterruptedException
    {
        A = new double[N][];
        for (int i = 0; i < N; i++)
            A[i] = new double[N];
        B = new double[N];
        //B = new double[N];
        X = new double[N];

        Barrier barrier = new Barrier();
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                A[i][j] = 1 + Math.random() * 10;
            }
            B[i] = Math.random() * 10;
        }

        //print();

        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[threadsCounter];
        for (int i = 0; i < threadsCounter; i++)
        {
            threads[i] = new Thread(new MyThread(i, barrier));
            threads[i].start();
        }
        for (int i = 0; i < threadsCounter; i++)
            threads[i].join();

        for (int i = N - 1; i >= 0; i--)
        {
            X[i] = B[i];
            for (int j = i + 1; j < N; j++)
                X[i] -= A[i][j] * X[j];
            X[i] /= A[i][i];
        }

        //print();

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(elapsed + " ms\n");
        checkResult();
    }
}

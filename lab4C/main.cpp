#include <sys/time.h>
#include <iostream>
#include <pthread.h>
#include <cmath>
#include <omp.h>
#define THREADS 10
const long long N = 2000; // матрица NxN
double **A2;
double *B2;
pthread_barrier_t barrier;

using namespace std;

void partialPivoting(int row, double **A, double *B)
{
    double ksave = -1.0f;
    int k_i = 0;   //номер строки, в котором будет найден наибольший элемент
    for (int col = row; col < N; col++)  // Найти самый большой элемент в текущем столбце
    {
        if (abs(A[col][row]) > ksave)
        {
            ksave = abs(A[col][row]);
            k_i = col;
        }
    }

    for (int col = row; col < N; col++)  //Поменять строки
    {
        double tmp = A[k_i][col];
        A[k_i][col] = A[row][col];
        A[row][col] = tmp;
    }

    double tmp = B[k_i];
    B[k_i] = B[row];
    B[row] = tmp;
}

void checkResult(double **A, const double *B, const double *X) //проверяет, вычисляя L2-норму Ax-b
{
    auto *r = new double[N];
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
    result = sqrt(result);
    cout << "Error is " << result << "\n";
}

void print(double **A) {
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
            cout << (int) A[i][j] << " ";
        cout << "\n";
    }
    cout << "\n\n";
}

void *gaussianElimination_pthreads(void *s)
{
    int threadID = *(int *) s; //id потока передан в качестве аргумента
    for (int row = 0; row < N; row++)
    {
        pthread_barrier_wait(&barrier); //синхронизация потоков(дожидается завершения всех потоков, прежде чем начнется следующий ряд)
        if (threadID == 0)
            partialPivoting(row, A2, B2);  //Поиск опорного элемента
        pthread_barrier_wait(&barrier); //дожидается завершения поиска опорного элемента
        double tf = row + threadID * (N - row) / (double) THREADS; //распределение строк влево
        int threadRow = (int) tf;
        for (int k = threadRow; k < round(tf + (N - row) / (double) THREADS); k++) //метод Гаусса, строки разделены на отдельные потоки
        {
            if (k <= row)
                continue;
            double m = A2[k][row] / A2[row][row];
            for (int col = row; col < N; col++)
                A2[k][col] -= m * A2[row][col];
            B2[k] -= m * B2[row];
        }
    }
    pthread_exit(nullptr);
}

void gaussianElimination_openMP(double **A, double* B)
{
    int k = 0;
    for (int row = 0; row < N; row++)
    {
        partialPivoting(row, A, B);  //Поиск опорного элемента
#pragma omp parallel num_threads(THREADS)
#pragma omp for private(k) schedule(dynamic)
        for (k = row + 1; k < N; k++)   //нормирование и вычитание
        {
            double m = A[k][row] / A[row][row];
            for (int col = row; col < N; col++)
                A[k][col] -= m * A[row][col];
            B[k] -= m * B[row];
        }
    }
}

int main()
{
    auto A1 = new double *[N]; //init для openmp
    A2 = new double *[N]; //копия для pthreads
    for (int i = 0; i < N; i++)
    {
        A1[i] = new double[N];
        A2[i] = new double[N];
    }
    auto B1 = new double[N];
    B2 = new double[N];
    auto X1 = new double[N];
    auto X2 = new double[N];
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
        {
            A1[i][j] = 1 + drand48() * 10;
            A2[i][j] = A1[i][j];
        }
        B1[i] = drand48() * 10;
        B2[i] = B1[i];
    }

    //print(A1);

    //openmp
    struct timeval tp{};  //Получить текущее системное время
    gettimeofday(&tp, nullptr);
    long int start = tp.tv_sec * 1000 + tp.tv_usec / 1000;
    gaussianElimination_openMP(A1, B1);  // старт алгоритма
    for (int i = N - 1; i >= 0; i--)  // Обратное решение
    {
        X1[i] = B1[i];
        for (int j = i + 1; j < N; j++)
            X1[i] -= A1[i][j] * X1[j];
        X1[i] /= A1[i][i];
    }
    gettimeofday(&tp, nullptr);
    long int end = tp.tv_sec * 1000 + tp.tv_usec / 1000;
    //print(A1);
    cout << "OpenMP. Execution time is " << end - start << " ms\n";
    checkResult(A1, B1, X1);






    //pthreads
    gettimeofday(&tp, nullptr);
    start = tp.tv_sec * 1000 + tp.tv_usec / 1000;
    pthread_barrier_init(&barrier, nullptr, THREADS);
    int thread_rows[THREADS]; //Установка данных потока
    pthread_t threads[THREADS];
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    for (int i = 0; i < THREADS; i++) //Назначение строк, которые обработает поток
    {
        thread_rows[i] = i;
        pthread_create(threads + i, &attr, gaussianElimination_pthreads, (void *) (thread_rows + i));
    }
    for (auto thread : threads) // Дождаться окончания метода Гаусса
        pthread_join(thread, nullptr);
    for (int i = N - 1; i >= 0; i--)   // Обратное решение
    {
        X2[i] = B2[i];
        for (int j = i + 1; j < N; j++)
            X2[i] -= A2[i][j] * X2[j];
        X2[i] /= A2[i][i];
    }
    gettimeofday(&tp, nullptr);
    end = tp.tv_sec * 1000 + tp.tv_usec / 1000;
    //print(A2);
    cout << "pthreads. Execution time is " << end - start << " ms\n";
    checkResult(A2, B2, X2);

    //освобождение векторов и матриц
    for (int i = 0; i < N; i++)
        delete[] A1[i];
    delete[] A1;
    delete[] B1;
    delete[] X1;
    for (int i = 0; i < N; i++)
        delete[] A2[i];
    delete[] A2;
    delete[] B2;
    delete[] X2;
}
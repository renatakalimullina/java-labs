cmake_minimum_required(VERSION 3.12)
project(kurs)

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)
find_package(OpenMP REQUIRED)


set(CMAKE_CXX_STANDARD 14)

add_executable(kurs main.cpp)
target_link_libraries(kurs  Threads::Threads OpenMP::OpenMP_CXX)
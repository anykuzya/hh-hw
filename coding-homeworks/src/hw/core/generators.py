import random


# написать генераторную функцию, которая принимает число N возвращает N рандомных чисел от 1 до 100
def gen(N):
    i = 0
    while i < N:
        yield random.randint(1, 100)
        i += 1


# написать генераторное выражение, которое делает то же самое
lambda N: (random.randint(1, 100) for x in range(0, N))

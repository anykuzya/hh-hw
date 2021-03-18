from decorators import cache_decorator
from enum import Enum


class InvalidOperationException(Exception):
    pass


class InputType(Enum):
    NUMBER = 'Введите число: '
    OPERATION = 'Введите операцию: '


@cache_decorator
def calculator(a, b, op):
    print("Calculating " + str(a) + op + str(b))
    if op == '+':
        return a + b
    if op == '-':
        return a - b
    if op == '/':
        return a / b
    if op == '*':
        return a * b
    if op == '**':
        return pow(a, b)


def read_input(input_type):
    prompt = input_type.value
    while True:
        try:
            if input_type == InputType.NUMBER:
                return int(input(prompt))
            else:
                op = input(prompt)
                if op not in ('+', '-', '/', '*', '**'):
                    raise InvalidOperationException
                return op
        except InvalidOperationException:
            print("Введите корректный оператор")
        except ValueError:
            print("Введите целое число")


if __name__ == '__main__':
    while True:
        a = read_input(InputType.NUMBER)
        b = read_input(InputType.NUMBER)
        operation = read_input(InputType.OPERATION)
        print('Результат: ', calculator(a, b, operation))

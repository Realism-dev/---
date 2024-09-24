package solutions
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем входные данные контеста
//    val (l, r) = readLine()!!.split(" ").map { it.toLong() }
    /* Тестовый вариант для запуска */
    val (l, r) = Pair(4, 7) // Вывод:4

    val validNumbers = mutableSetOf<Long>()
    // Проходим по всем возможным цифрам от 1 до 9
    for (digit in 1..9) {
        var number = digit.toLong() // Начинаем с числа, состоящего из одной цифры
        while (number <= r) {
            if (number >= l) {
                validNumbers.add(number) // Добавляем число в множество, если оно в диапазоне
            }
            number = number * 10 + digit // Генерируем следующее число из одинаковых цифр
        }
    }

    // Выводим количество уникальных тестов
    println(validNumbers.size)
}
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем количество чисел и число операций
    val (n, k) = readLine()!!.split(" ").map { it.toInt() }
    // Считываем числа
    val numbers = readLine()!!.split(" ").map { it.toLong() }

    // Массив для хранения выгод от замены цифр
    val profits = mutableListOf<Long>()

    // Проходим по каждому числу
    for (number in numbers) {
        val strNumber = number.toString()
        val len = strNumber.length

        // Проходим по каждой цифре и вычисляем выгоду при замене на 9
        for (i in strNumber.indices) {
            val digit = strNumber[len - 1 - i] // берем цифру с конца
            val changeProfit = (9 - digit.digitToInt()) * Math.pow(10.0, i.toDouble()).toLong()
            if (changeProfit > 0) {
                profits.add(changeProfit)
            }
        }
    }

    // Сортируем выгоды по убыванию
    profits.sortDescending()

    // Суммируем максимальные прибыли от первых k замен
    val maxProfit = profits.take(k).sum()

    // Выводим результат
    println(maxProfit)
}
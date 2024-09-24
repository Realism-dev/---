package solutions
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
const val MAX_SIZE = 120 // Максимальный размер массива
fun main() {
    // Считываем входные данные контеста
//    val numberOfElements = readLine()!!.toInt()
//    if (numberOfElements == 0) {
//        println(0) // Если количество обедов равно 0, выводим 0 и выходим
//        return
//    }
//    val elements = IntArray(solutions.MAX_SIZE) // Массив для хранения цен обедов
//    for (i in 1..numberOfElements) { // Считываем цены обедов
//        elements[i] = readLine()!!.toInt()
//    }
    /* Тестовый вариант для запуска */
    val numberOfElements = 5
    val elements = IntArray(MAX_SIZE)
    intArrayOf(0, 35, 40, 101, 59, 63).copyInto(elements)

    // Инициализация динамического программирования
    val INF = MAX_SIZE * numberOfElements + 300 // Значение для инициализации
    val dp = Array(MAX_SIZE) { IntArray(MAX_SIZE) { INF } } // Массив для хранения минимальных затрат
    dp[0][0] = 0 // Начальное условие

    // Основной алгоритм для вычисления минимальных затрат
    for (i in 1..numberOfElements) {
        for (j in 0..i) {
            // Минимизируем значение dp[i][j]
            dp[i][j] = minOf(dp[i][j], dp[i - 1][j] + elements[i])

            // Если текущий элемент больше 100, рассматриваем возможность увеличения j
            if (elements[i] > 100) {
                dp[i][j + 1] = minOf(dp[i][j + 1], dp[i - 1][j] + elements[i])
            }

            // Если j больше или равно 1, рассматриваем уменьшение j
            if (j >= 1) {
                dp[i][j - 1] = minOf(dp[i][j - 1], dp[i - 1][j])
            }
        }
    }

    // Находим минимальное значение в последней строке dp
    var minimumResult = INF
    for (j in 0..numberOfElements) {
        minimumResult = minOf(minimumResult, dp[numberOfElements][j])
    }

    // Выводим результат
    println(minimumResult)
}
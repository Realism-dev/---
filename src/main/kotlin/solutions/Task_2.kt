package solutions
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем входные данные контеста
//    val N = readLine()!!.toLong()
    /* Тестовый вариант для запуска */
    val N = 6 // Вывод: 3
    var cuts = 0
    var currentParts: Long = 1

    while (currentParts < N) {
        cuts++ // Увеличиваем количество разрезов
        currentParts *= 2 // Увеличиваем количество частей вдвое
    }
    // Выводим результат
    println(cuts)
}
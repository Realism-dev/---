/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем количество частей
    val N = readLine()!!.toLong()
    var cuts = 0
    var currentParts:Long = 1

    while (currentParts < N) {
        cuts++ // Увеличиваем количество разрезов
        currentParts *= 2 // Увеличиваем количество частей вдвое
    }
    // Выводим результат
    println(cuts)
}
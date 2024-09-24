/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем количество сотрудников и время
    val (_, t) = readLine()!!.split(" ").map { it.toInt() }
    // Считываем этажи, на которых находятся сотрудники
    val floors = readLine()!!.split(" ").map { it.toInt() }
    // Считываем индекс уходящего сотрудника
    val index = readLine()!!.toInt() - 1

    // Находим минимум и максимум этажей
    val minFloor = floors.first()
    val maxFloor = floors.last()

    // Этаж, на котором находится уходящий сотрудник
    val urgentFloor = floors[index]

    // Если времени достаточно, чтобы посетить всех сотрудников
    if (maxFloor - urgentFloor <= t || urgentFloor - minFloor <= t) {
        // В этом случае просто проходим все этажи
        println(maxFloor - minFloor)
        return
    }

    // Если времени недостаточно, то вычисляем два возможных пути
    // Первый вариант: сначала к уходящему, потом вверх и вниз
    val option1 = Math.abs(maxFloor - urgentFloor) + Math.abs(maxFloor - minFloor)
    // Второй вариант: сначала к уходящему, потом вниз и вверх
    val option2 = Math.abs(urgentFloor - minFloor) + Math.abs(maxFloor - minFloor)

    // Находим минимальный из двух вариантов
    val result = Math.min(option1, option2)

    // Выводим результат
    println(result)
}
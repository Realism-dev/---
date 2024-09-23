/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    val n = readLine()!!.toInt() // Считываем количество вершин
    val vertices = Array(n) { IntArray(2) } // Массив для хранения координат вершин
    for (i in 0 until n) {
        vertices[i] = readLine()!!.split(" ").map { it.toInt() }.toIntArray() // Считываем координаты
    }

    // Если вершина одна, выводим координату X этой вершины
    if (n == 1) {
        println("%.6f".format(vertices[0][0].toDouble()))
        return
    }

    // Если вершины две, выводим координату центра линии, образованной двумя вершинами
    if (n == 2) {
        val centerX = (vertices[0][0] + vertices[1][0]) / 2.0
        println("%.6f".format(centerX))
        return
    }

    // Находим половинную площадь
    val halfArea = calculatePolygonArea(vertices) / 2.0

    // Определяем переменные для бинарного поиска
    var left = vertices.minOf { it[0] }.toDouble() // Минимальная x-координата
    var right = vertices.maxOf { it[0] }.toDouble() // Максимальная x-координата
    val precision = 1e-7 // Точность для бинарного поиска
    var leftArea:Double = 0.0 // Площадь левой части многоугольника
    var mid:Double = 0.0 // Текущая x-координата

    // Начинаем бинарный поиск в цикле
    while (true) {
        mid = (left + right) / 2
        leftArea = calculateLeftArea(vertices, mid)
        // Если разница площадей меньше заявленной точности, прерываем цикл
        if(Math.abs(halfArea - leftArea) <= precision) break
        // Сравниваем площади
        if (leftArea < halfArea) {
            left = mid // Если площадь меньше, ищем вправо
        } else {
            right = mid // Если площадь больше или равна, ищем влево
        }
    }

    // Выводим значение X с точностью 6 знаков после запятой
    println("%.6f".format((left + right) / 2))
}

// Функция для вычисления площади многоугольника
fun calculatePolygonArea(vertices: Array<IntArray>): Double {
    var area = 0.0
    for (i in 0 until vertices.size) {
        val j = (i + 1) % vertices.size
        area += (vertices[i][0] *vertices[j][1] - vertices[j][0]* vertices[i][1])
    }
    return Math.abs(area) / 2.0
}

// Функция для вычисления площади левой части многоугольника по заданной x
fun calculateLeftArea(vertices: Array<IntArray>, x: Double): Double {
    var area = 0.0
    val n = vertices.size

    for (i in 0 until n) {
        val j = (i + 1) % n
        val x1 = vertices[i][0]
        val y1 = vertices[i][1]
        val x2 = vertices[j][0]
        val y2 = vertices[j][1]

        // Проверяем, пересекает ли линия x
        if (x1 <= x && x2 <= x) {
            // Оба конца слева от x
            area += (x2 - x1) * (y2 + y1) / 2.0
        } else if (x1 >= x && x2 >= x) {
            // Оба конца справа от x, не добавляем площадь
            continue
        } else {
            // Одна вершина слева, другая справа
            val intersectY = y1 + (y2 - y1) * (x - x1) / (x2 - x1) // Находим y при пересечении
            if (x1 < x) {
                // Если x1 слева, добавляем площадь
                area += (x - x1) * (y1 + intersectY) / 2.0
            } else {
                // Если x2 слева, добавляем площадь
                area += (x2 - x) * (intersectY + y2) / 2.0
            }
        }
    }
    return Math.abs(area)
}
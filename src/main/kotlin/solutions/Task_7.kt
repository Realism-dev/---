package solutions
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем входные данные контеста
//    val n = readLine()!!.toInt()
//    val a = readLine()!!.split(" ").map { it.toInt() }.toIntArray()
    /* Тестовый вариант для запуска */
    val n = 3
    val a = listOf(1, 3, 1).toIntArray() // Вывод: 1 2

    var result = "-1 -1"

    // Определяем число вхождений ребер для каждого ученика в графе
    var inCount: Int
    val doubleIncomingIndexes = mutableListOf<Int>()
    val zeroIncomingPupils = mutableListOf<Int>()

    // Массив для подсчета входящих рёбер
    val incoming = IntArray(n + 1) // +1 для удобной работы с индексами дальше

    // Подсчет входящих рёбер
    for (i in 1..n) {
        incoming[a[i - 1]]++
    }
    // Ищем учеников с 0 и 2 вхождениями
    for (puple in 1..n) {
        inCount = incoming[puple]
        if (inCount == 2) {
            doubleIncomingIndexes.add(a.indexOfFirst { it == puple })
            doubleIncomingIndexes.add(a.indexOfLast { it == puple })
        }
        if (inCount == 0) {
            zeroIncomingPupils.add(puple)
        }

        // Если вхождений больше 2 выводим -1 -1
        if (inCount > 2) {
            println("-1 -1")
            return
        }
    }
    // Если такие ученики есть, пытаемся изменить назначение
    if (doubleIncomingIndexes.count() == 2 && zeroIncomingPupils.count() == 1) {
        doubleIncomingIndexes.forEach {
            val aCopy = a.copyOf()
            aCopy[it] = zeroIncomingPupils[0]
            if (isCycle(aCopy)) {
                result = (it + 1).toString() + " " + zeroIncomingPupils[0].toString()
                println(result)
                return
            }
        }
    }
    // Выводим результат
    println(result)
}

// Проверка, образует ли граф цикл
fun isCycle(a: IntArray): Boolean {
    val n = a.size
    val visited = BooleanArray(n + 1)
    var current = 1
    var count = 0

    while (!visited[current]) {
        visited[current] = true
        current = a[current - 1]
        count++
    }

    return current == 1 && count == n
}

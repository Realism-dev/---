/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Начальная сумма, для расчетов она не нужна
    val startSum = 1L

    // Считываем значение N
    val N = readLine()!!.toLong() - startSum

    // Считываем номиналы A, B, C
    val coins = readLine()!!.split(" ").map { it.toInt() }

    val mod = coins.maxOrNull() ?: 0
    val fullCycles = N / mod // Считаем общее число циклов
    val fullRemainder = N % mod // Определяем итоговые остатки

    // Массив для хранения минимального количества циклов для каждого остатка
    val reachableRemainders = IntArray(mod) { -1 }
    reachableRemainders[0] = 1 // Остаток 0 достижим сразу

    // Очередь для хранения текущих достижимых остатков и количества циклов до них
    val queue = ArrayDeque<Pair<Int, Int>>() // Пара: (остаток, количество циклов)
    queue.add(0 to 1)

    // Пока есть остатки для проверки
    while (queue.isNotEmpty()) {
        val (remainder, cycles) = queue.removeFirst()

        // Проверяем все монеты
        for (coin in coins) {
            // Вычисляем новый остаток
            val newRemainder = (remainder + coin) % mod
            val newCycles = cycles + (remainder + coin) / mod

            // Если остаток ещё не был достигнут или достигнут с меньшим числом циклов
            if (reachableRemainders[newRemainder] == -1 || reachableRemainders[newRemainder] > newCycles) {
                reachableRemainders[newRemainder] = newCycles
                queue.add(newRemainder to newCycles)
            }
        }
    }

    // Помечаем недостижимые остатки для суммы N
    for (x in reachableRemainders.indices) {
        val startSum = (reachableRemainders[x] * mod - (mod - x)).toLong()
        if (startSum > N || reachableRemainders[x] > fullCycles + 1) reachableRemainders[x] = -1
    }

    var sumCount = 1L // сумму 0 можно получить всегда

    // Считаем количество достижимых сумм по остаткам
    for (x in reachableRemainders.indices) {
        // Исключаем недостижимые остатки
        if (reachableRemainders[x] < 0) continue

        // Проверяем достижимость итоговых остатков N % mod
        if (x > 0 && x <= fullRemainder) sumCount++
        sumCount += fullCycles - reachableRemainders[x] + 1
    }

    println(sumCount)
}

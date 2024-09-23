/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
import kotlin.random.Random

fun main() {
    val testCases = 1000
    val upperLimitR = 20_000_000
    val upperLimitP = 1_000_000_000

    // Генерация простых чисел до 10^9
    val primes = generatePrimes(upperLimitP)

    val results = mutableListOf<String>()

    repeat(testCases) {
        // Генерация l и r
        val r = Random.nextLong(1, (upperLimitR + 1).toLong())
        val l = Random.nextLong(1, r + 1)
        // Случайный индекс для выбора простого числа
        val p = primes.filter { it > r }[Random.nextInt((primes.size - r).toInt())]

        // Вычисление суммы обратных значений
        val sum = calculateSumOfInverses(l, r, p)

        // Формирование строки результата
        results.add("\"$l $r $p $sum\",")
    }

    // Вывод результатов
    results.forEach { println(it) }
}

// Функция для генерации простых чисел до n
fun generatePrimes(n: Int): List<Int> {
    val isPrime = BooleanArray(n + 1) { true }
    isPrime[0] = false
    isPrime[1] = false

    for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (isPrime[i]) {
            for (j in i * i..n step i) {
                isPrime[j] = false
            }
        }
    }

    return (2..n).filter { isPrime[it] }
}

// Функция для вычисления суммы обратных значений
fun calculateSumOfInverses(l: Long, r: Long, p: Int): Long {
    var sum = 0L
    for (x in l..r) {
        sum += modInverse(x, p.toLong()) // Приведение к Int
    }
    return sum % p
}

// Функция для вычисления модульного обратного числа
fun modInverse(a: Long, p: Long): Long {
    // Применение расширенного алгоритма Евклида для нахождения обратного элемента
    var y = 0L
    var x = 1L
    if (p == 1L) return 0 // Обратное значение не существует, если p = 1

    var a1 = a
    var pMod = p // используем pMod для изменения, чтобы не менять исходное p
    var q :Long
    var t: Long
    while (a1 > 1) {
        // q - коэффициент, который мы будем использовать
        q = a1 / pMod
        t = pMod

        // m - остаток
        pMod = a1 % pMod
        a1 = t
        t = y

        y = x - q * y
        x = t
    }

    // Убедимся, что x положительное
    if (x < 0) x += p

    return x
}

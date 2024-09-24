/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем данные
    val input = readLine()!!.split(" ")
    val l = input[0].toInt()
    val r = input[1].toInt()
    val p = input[2].toInt()
    if (p < 2 || p <= r || l < 1 || r < 1 || l > r) {
        println(0)
        return
    }
    val f = LongArray(r-l+1) // массив для "префиксов"

    // Вычисляем префиксные произведения: f(k) = f(k-1) * k % p
    for (k in f.indices ) {
        if(k == 0) f[0] = (l % p).toLong()
        else f[k] = f[k - 1] * (l+k) % p
    }

    // Определяем обратное по модулю p для последнего "префикса"
    var fInverse = modInverse(f.last(), p) // f(k)^-1
    var sum = 0L

    // Считаем обратные числа F(k) = f(k)^-1 * f(k-1)% p
    for (k in r - l downTo 1) {
        sum += (fInverse * f[k-1])%p
        fInverse = (fInverse * (k+l)) % p
    }
    sum += fInverse

    // Выводим результат - сумма обратных чисел по модулю p
    println(sum%p)
}

// Функция для определения обратного по модулю p,
// используя малую теорему Ферма: a^(p-2) % p
fun modInverse(a: Long, p: Int): Long {
    return power(a, (p - 2).toLong(), p.toLong())
}

// Функция для быстрого возведения в степень: (base^exp) % mod
fun power(base: Long, exp: Long, mod: Long): Long {
    var res = 1L
    var b = base
    var e = exp

    while (e > 0) {
        if (e % 2 == 1L) {
            res = res * b % mod
        }
        b = b * b % mod
        e /= 2
    }
    return res
}
/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 *
 */
fun main(){
    // Считываем входные данные
    val input = readLine()!!.split(" ").map { it.toInt() }
    val a = input[0]
    val b = input[1]
    val c = input[2]
    val d = input[3]
    // Считаем сумму
    val result = if(d>b)
        a + c*(d-b)
    else a
    // Выводим результат
    println(result)
}
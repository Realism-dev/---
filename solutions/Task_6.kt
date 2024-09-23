/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
fun main() {
    // Считываем данные
    readLine()
    val numbers = readLine()!!.split(" ").toTypedArray()
    var res = "-1 -1"
    // Создаем копию для замены чисел
    val changedNum = numbers.copyOf()
    // Проверяем, что элемент лежит на своем месте:
    // четное значение под четным порядковым номером и наоборот
    var checkedMap = numbers.mapIndexed{i,v-> isEven(i+1) == isEven(v.toInt())}
    // Считаем количество элементов не на своих местах
    var cntFailed = checkedMap.count{it==false}
    // Рассмотрим краевой случай, при котором все числа на своих местах:
    // [1,2,3] -> 1 3
    // [1,2,3,4] -> 1 3 || 2 4
    if(cntFailed==0 && numbers.size>=3) res = "1 3"
    // По условию задачи можно заменить ровно одну пару чисел
    if(cntFailed==2)
    {
        // Определяем индексы элементов не на своих местах
        val fixedIndexes = mutableListOf<Int>()
        checkedMap.filterIndexed{i,v->
            if(v==false)
                fixedIndexes.add(i)
            true
        }
        // Меняем элементы местами
        changedNum[fixedIndexes.first()] = numbers[fixedIndexes.last()]
        changedNum[fixedIndexes.last()] = numbers[fixedIndexes.first()]
        // Проверяем положение элементов повторно
        checkedMap = changedNum.mapIndexed{i,v-> isEven(i+1) == isEven(v.toInt())}
        cntFailed = checkedMap.count{it==false}
        // Если все элементы оказались на своих местах выводим результат
        //(1≤i,j≤n,i!=j)
        if(cntFailed==0)
            res = fixedIndexes
                .map{it+1} // Приводим к 1-ориентированной нумерации
                .joinToString(" ")
    }

    println(res)
}

// true = even, false = odds
fun isEven(a:Int):Boolean{
    return a%2==0
}
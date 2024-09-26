package tests
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import java.util.stream.Stream

/*
* Класс для тестирования решений
* */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestSolution {
    private val results = mutableListOf<TestResult>()
    private lateinit var testTask:TestTask

    /*
    * Установить класс задачи для тестирования
    * */
    @BeforeAll
    fun setUp() {
        // Тестируемая задача
        testTask = TestTask_1()
    }

    /*
    * Тестовые данные
    * */
    private fun dataProvider(): Stream<Arguments> {
        return testTask.userTestData()
    }

    /*
    * Тестирование
    * */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testSolution(inputData: String, expectedOutput: String) {
        // Перенаправление стандартного ввода
        val inputStream: InputStream = ByteArrayInputStream(inputData.map{ it.code.toByte() }.toByteArray())
        val originalIn = System.`in` // Сохраняем оригинальный System.in
        System.setIn(inputStream) // Устанавливаем новый InputStream

        // Перенаправление вывода
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val originalOut = System.out   // Сохраняем оригинальный System.out
        System.setOut(printStream) // Устанавливаем новый PrintStream

        // Время запуска теста
        val startTime = System.nanoTime()
        // Измеряем начальное использование памяти
        val runtime = Runtime.getRuntime()
        val memoryBefore = runtime.totalMemory() - runtime.freeMemory()

        // Выполнение вашего метода main
        testTask.yourSolution() // Предполагаем, что main() не принимает аргументов и читает из System.in

        // Измеряем конечное использование памяти
        val memoryAfter = runtime.totalMemory() - runtime.freeMemory()
        // Вычисляем изменение памяти
        val memoryUsed = memoryAfter - memoryBefore
        // Определяем итоговую длительность выполнения
        val endTime = System.nanoTime()
        val durationInMillis = (endTime - startTime) / 1_000_000

        // Восстановление оригинального ввода
        System.setIn(originalIn)
        // Восстановление оригинального вывода
        System.setOut(originalOut)

        // Получение захваченного вывода
        val actualOutput = outputStream.toString().trim() // Убираем лишние пробелы

        // Проверка результата
        assertEquals(expectedOutput, actualOutput)

        // Сохраняем результат
        results.add(TestResult(inputData, expectedOutput, actualOutput,durationInMillis,memoryUsed))
    }

    /*
    * Вывод итоговых результатов тестирования
    * */
    @AfterAll
    fun printResults(): Unit {
        println("Summary of Test Results:")
        var failedTests = 0
        var failedByTime = 0
        var failedByMemory = 0
        var passedTests = 0

        results.forEach { (inputData, expected, actual,duration, memory) ->
            val input = inputData.replace("\n","")
            // Сравниваем результат с ожидаемым
            if (actual != expected) {
                println("Тест не пройден для $input: ожидалось $expected, получено $actual")
                failedTests++
            } else {
                if(duration > MAX_DURATION_MS || memory/1024/1024 > MAX_MEMORY_MB )
                {
                    if(duration > MAX_DURATION_MS) failedByTime++
                    if(memory/1024/1024 > MAX_MEMORY_MB ) failedByMemory++
                    println("Ввод: $input Время:${duration} ms  память: ${memory / 1024/1024} MB ")
                }
                passedTests++
            }
        }
        // Вывод общего количества пройденных и не пройденных тестов
        println("Пройдено: $passedTests, не пройдено: $failedTests")
        println("Завалено по: Времени: $failedByTime Памяти:$failedByMemory")
    }

    // Ограничения по памяти и времени в Вечном контесте
    companion object{
        const val MAX_MEMORY_MB = 256
        const val MAX_DURATION_MS = 1000
    }

    data class TestResult(
        val inputData: String,
        val expectedOutput: String,
        val actualOutput:String,
        val duration: Long,
        val memory: Long
        )
}
interface TestTask{
    val testFilter:TestFilter
    fun userTestData():Stream<Arguments>
    fun yourSolution()
}

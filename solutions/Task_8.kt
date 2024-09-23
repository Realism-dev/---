/*
 * Copyright (c) 2024 Realism-dev
 *
 * Этот файл лицензирован в соответствии с MIT License.
 * Подробности см. в LICENSE файле в корне проекта.
 */
operator fun <T> List<T>.component6(): T = get(5)
operator fun <T> List<T>.component7(): T = get(6)
operator fun <T> List<T>.component8(): T = get(7)
fun main() {
    // Считываем данные
    val first = readLine()!!.split(" ").map { it.toDouble() }.toDoubleArray()
    val second = readLine()!!.split(" ").map { it.toDouble() }

    val tc = TestCase(
        params = Pair(first[0], first[1]),
        values = second
    )

    test(tc)
}

fun test(testCase: TestCase) {
    val (x, y) = solve(testCase)
    println(String.format("%.6f %.6f", x, y))
}

fun solve(testCase: TestCase): Pair<Double, Double> {
    val (A, D) = testCase.params
    val (Ax, Ay, Bx, By, _, _, Dx, Dy) = testCase.values

    val m = Matrix2x2(arrayOf(
        doubleArrayOf(A - Bx + Ax, -Dx + Ax),
        doubleArrayOf(-By + Ay, D - Dy + Ay)
    ))

    val (x, y) = m.solveEq(Ax, Ay)
    return Pair(x *A, y* D)
}

class Matrix2x2(private val rows: Array<DoubleArray>) {
    fun det(): Double {
        return rows[0][0] *rows[1][1] - rows[0][1]* rows[1][0]
    }

    private fun replaceCol(col: Int, a1: Double, a2: Double): Matrix2x2 {
        val newRows = rows.map { it.clone() }.toTypedArray()
        newRows[0][col] = a1
        newRows[1][col] = a2
        return Matrix2x2(newRows)
    }

    fun solveEq(x: Double, y: Double): Pair<Double, Double> {
        val d0 = det()
        return Pair(
            replaceCol(0, x, y).det() / d0,
            replaceCol(1, x, y).det() / d0
        )
    }
}

data class TestCase(
    val params: Pair<Double, Double>,
    val values: List<Double>
)

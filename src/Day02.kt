import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun incOrDec(a: Int, b: Int): Int {
        if (a > b) {
            return 1
        }
        if (a == b) {
            return 0
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val lines = input.map { l -> l.split(" ").map { it.toInt() } }

        var count = 0

        outer@ for (line in lines) {
            val sign = incOrDec(line[0], line[1])
            if (sign == 0) {
                continue
            }
            for (i in 0..<line.size - 1) {
                if (incOrDec(line[i], line[i + 1]) != sign) {
                    continue@outer
                }

                val diff = abs(line[i] - line[i + 1])
                if (diff < 1 || diff > 3) {
                    continue@outer
                }
            }
            count++
        }

        return count
    }

    fun part2(input: List<String>): Int {
        fun safe(line: List<Int>): Boolean {
            val sign = incOrDec(line[0], line[1])
            if (sign == 0) {
                return false
            }
            for (i in 0..<line.size - 1) {
                if (incOrDec(line[i], line[i + 1]) != sign) {
                    return false
                }

                val diff = abs(line[i] - line[i + 1])
                if (diff < 1 || diff > 3) {
                    return false
                }
            }
            return true
        }

        val lines = input.map { l -> l.split(" ").map { it.toInt() } }

        var count = 0
        outer@for (line in lines) {
            if (safe(line)) {
                count++
                continue
            }

            for (i in line.indices) {
                if (safe(line.filterIndexed { index, _ -> index != i })) {
                    count++
                    continue@outer
                }
            }
        }

        return count
    }

    val testInput = readInput("day2_test")

    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day2")
    println(part1(input))
    println(part2(input))
}
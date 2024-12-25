fun main() {
    fun toHeights(item: List<String>): List<Int> {
        val heights = mutableListOf(0, 0, 0, 0, 0)
        for (i in 1..<item.size - 1) {
            for (j in item[i].indices) {
                if (item[i][j] == '#') {
                    heights[j]++
                }
            }
        }

        return heights
    }

    fun part1(input: List<String>): Int {
        val locksAndKeys = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        val locks = locksAndKeys.filter {
            it[0] == "#####"
        }.map { toHeights(it) }

        val keys = locksAndKeys.filter {
            it[0] == "....."
        }.map { toHeights(it) }

        var count = 0

        for (key in keys) {
            lock@for (lock in locks) {
                for (i in key.indices) {
                    if (lock[i] + key[i] > 5) {
                        continue@lock
                    }
                }
                count++
            }
        }

        return count
    }

//    fun part2(input: List<String>): Int {
//
//    }

    val testInput = readInput("day25_test")

    check(part1(testInput) == 3)
//    check(part2(testInput) == 0)

    val input = readInput("day25")
    println(part1(input))
//    println(part2(input))
}
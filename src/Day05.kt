fun main() {
    fun correct(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        for (i in update.indices) {
            val pageNum = update[i]
            val before = rules.filter { it.second == pageNum }.map { it.first }
            val after = rules.filter { it.first == pageNum }.map { it.second }

            val valuesBefore = update.subList(0, i)
            val valuesAfter = update.subList(i + 1, update.size)

            for (value in valuesBefore) {
                if (after.contains(value)) return false
            }

            for (value in valuesAfter) {
                if (before.contains(value)) return false
            }
        }

        return true
    }

    fun reorder(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        return update.sortedWith { a, b ->
            val relevantRule = rules.filter { it.first == a && it.second == b || it.first == b && it.second == a }[0]

            if (relevantRule.first == a) {
                return@sortedWith -1
            } else {
                return@sortedWith 1
            }
        }
    }

    fun part1(input: List<String>): Int {
        val raw = input.joinToString("\n")
        val parts = raw.split("\n\n").map { it.split("\n") }

        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()

        for (line in parts[0]) {
            val rule = line.split("|").map { it.toInt() }

            rules.add(Pair(rule[0], rule[1]))
        }

        for (line in parts[1]) {
            val update = line.split(",").map { it.toInt() }

            updates.add(update)
        }

        val correctUpdates = updates.filter { correct(it, rules) }

        var count = 0

        for (update in correctUpdates) {
            count += update[update.size / 2]
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val raw = input.joinToString("\n")
        val parts = raw.split("\n\n").map { it.split("\n") }

        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()

        for (line in parts[0]) {
            val rule = line.split("|").map { it.toInt() }

            rules.add(Pair(rule[0], rule[1]))
        }

        for (line in parts[1]) {
            val update = line.split(",").map { it.toInt() }

            updates.add(update)
        }

        val incorrectUpdates = updates.filter { !correct(it, rules) }
        val correctedUpdates = incorrectUpdates.map { reorder(it, rules) }

        var count = 0

        for (update in correctedUpdates) {
            count += update[update.size / 2]
        }

        return count
    }

    val testInput = readInput("day5_test")

    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("day5")
    println(part1(input))
    println(part2(input))
}
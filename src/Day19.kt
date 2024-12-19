fun main() {
    val cacheCanMake = mutableMapOf<Pair<String, List<String>>, Boolean>()
    fun canMake(desired: String, available: List<String>): Boolean {
        if (cacheCanMake.containsKey(desired to available)) {
            return cacheCanMake[desired to available]!!
        }

        if (desired.length == 0) {
            cacheCanMake[desired to available] = true
            return true
        }

        for (design in available.filter { desired.startsWith(it) }) {
            if (canMake(desired.drop(design.length), available)) {
                cacheCanMake[desired to available] = true
                return true
            }
        }

        cacheCanMake[desired to available] = false
        return false
    }

    fun part1(input: List<String>): Int {
        val availableDesigns = input[0].split(", ").sortedBy { it.length }.reversed()

        val desired = input.subList(2, input.size)

        return desired.count { canMake(it, availableDesigns) }
    }

    val cacheWaysToMake = mutableMapOf<Pair<String, List<String>>, Long>()
    fun numWaysToMake(desired: String, available: List<String>): Long {
        if (cacheWaysToMake.containsKey(desired to available)) {
            return cacheWaysToMake[desired to available]!!
        }

        if (desired.length == 0) {
            cacheWaysToMake[desired to available] = 1
            return 1
        }

        var sum = 0L
        for (design in available.filter { desired.startsWith(it) }) {
            sum += numWaysToMake(desired.drop(design.length), available)
        }

        cacheWaysToMake[desired to available] = sum
        return sum
    }

    fun part2(input: List<String>): Long {
        val availableDesigns = input[0].split(", ").sortedBy { it.length }.reversed()

        val desired = input.subList(2, input.size)

        return desired.sumOf { numWaysToMake(it, availableDesigns) }
    }

    val testInput = readInput("day19_test")

    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    val input = readInput("day19")
    println(part1(input))
    println(part2(input))
}
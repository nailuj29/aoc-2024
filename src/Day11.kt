fun main() {
    fun part1(input: List<String>): Int {
        var stones = input[0].split(" ").map { it.toLong() }

        for (i in 1..25) {
            val newStones = mutableListOf<Long>()
            for (stone in stones) {
                if (stone == 0L) {
                    newStones.add(1)
                } else if (stone.toString().length % 2 == 0) {
                    val string = stone.toString()
                    newStones.add(string.substring(0, string.length / 2).toLong())
                    newStones.add(string.substring(string.length / 2).toLong())
                } else {
                    newStones.add(stone * 2024)
                }

                stones = newStones
            }
        }

        return stones.size
    }

    val cache = mutableMapOf<Pair<Long, Int>, Long>()
    fun newStoneCount(stone: Long, depth: Int): Long {
        if (cache.containsKey(stone to depth)) {
            return cache[stone to depth]!!
        }
        if (depth == 75) {
            return 1
        }
        if (stone == 0L) {
            val count = newStoneCount(1, depth + 1)
            cache[stone to depth] = count
            return count
        } else if (stone.toString().length % 2 == 0) {
            val string = stone.toString()
            val count = newStoneCount(string.substring(0, string.length / 2).toLong(), depth + 1) +
                newStoneCount(string.substring(string.length / 2).toLong(), depth + 1)
            cache[stone to depth] = count
            return count
        } else {
            val count = newStoneCount(stone * 2024, depth + 1)
            cache[stone to depth] = count
            return count
        }
    }

    fun part2(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }

        val counts = stones.map { newStoneCount(it, 0) }

        return counts.sum()
    }

    val testInput = readInput("day11_test")

    check(part1(testInput) == 55312)

    val input = readInput("day11")
    println(part1(input))
    println(part2(input))
}
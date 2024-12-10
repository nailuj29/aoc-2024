fun main() {
    fun findAllLocations(grid: List<String>, num: Char): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == num) {
                    list.add(Pair(i, j))
                }
            }
        }

        return list
    }

    fun findReachableNines(pos: Pair<Int, Int>, grid: List<String>): Set<Pair<Int, Int>> {
        val currHeight = grid[pos.first][pos.second] - '0'
        if (currHeight == 9) return setOf(pos)
        val reachable = mutableSetOf<Pair<Int, Int>>()
        for (dir in listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)) {
            try {
                val nextPos = pos.first + dir.first to pos.second + dir.second
                if (grid[nextPos.first][nextPos.second] - '0' == currHeight + 1) {
                    reachable += findReachableNines(nextPos, grid)
                }
            } catch (ioobe: IndexOutOfBoundsException) {}
        }

        return reachable
    }

    fun score(pos: Pair<Int, Int>, grid: List<String>): Int {
        val currHeight = grid[pos.first][pos.second] - '0'
        if (currHeight == 9) return 1
        var score = 0
        for (dir in listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)) {
            try {
                val nextPos = pos.first + dir.first to pos.second + dir.second
                if (grid[nextPos.first][nextPos.second] - '0' == currHeight + 1) {
                    score += score(nextPos, grid)
                }
            } catch (ioobe: IndexOutOfBoundsException) {}
        }

        return score
    }

    fun part1(input: List<String>): Int {
        val trailHeads = findAllLocations(input, '0')

        var total = 0
        for (trailHead in trailHeads) {
            total += findReachableNines(trailHead, input).size
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val trailHeads = findAllLocations(input, '0')

        var total = 0
        for (trailHead in trailHeads) {
            total += score(trailHead, input)
        }

        return total
    }

    val testInput = readInput("day10_test")

    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("day10")
    println(part1(input))
    println(part2(input))
}
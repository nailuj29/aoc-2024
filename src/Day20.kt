import kotlin.math.abs

fun main() {
    fun heuristic(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return abs(end.first - start.first) + abs(end.second - start.second)
    }

    // definitely not needed here 💀
    fun astar(grid: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val openSet = mutableListOf(start)
        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        val gscore = mutableMapOf(start to 0)
        val fscore = mutableMapOf(start to heuristic(start, end))

        while (openSet.isNotEmpty()) {
            val current = openSet.minBy { fscore[it] ?: 10000000 }

            if (current == end) {
                val path = mutableSetOf(current)
                var thisNode = current
                while (thisNode in cameFrom.keys) {
                    thisNode = cameFrom[thisNode]!!
                    path.add(thisNode)
                }
                return path
            }

            openSet.remove(current)
            for (dir in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
                val neighbor = dir.first + current.first to dir.second + current.second
                try {
                    if (grid[neighbor.second][neighbor.first] == '#') continue
                } catch (ioobe: IndexOutOfBoundsException) {
                    continue
                }
                val tentativeGScore = (gscore[current] ?: 10000000) + 1
                if (tentativeGScore < (gscore[neighbor] ?: 10000000)) {
                    cameFrom[neighbor] = current
                    gscore[neighbor] = tentativeGScore
                    fscore[neighbor] = tentativeGScore + heuristic(neighbor, end)
                    if (openSet.none { it == neighbor }) {
                        openSet.add(neighbor)
                    }
                }
            }
        }

        return emptySet()
    }

    fun part1(input: List<String>): Int {
        var start = -1 to -1
        var end = -1 to -1
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == 'S') {
                    start = x to y
                } else if (input[y][x] == 'E') {
                    end = x to y
                }
            }
        }

        val fairPath = astar(input, start, end)

        val possibleCheats = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (n1 in fairPath) {
            inner@for (n2 in fairPath - n1) {
                if (n1 to n2 in possibleCheats) {
                    continue@inner
                }
                if (heuristic(n1, n2) <= 2) {
                    val saved = astar(input, n1, n2).size - 3
                    if (saved >= if (input.size > 100) 100 else 20) {
                        possibleCheats.add(n1 to n2)
                        possibleCheats.add(n2 to n1)
                    }
                }
            }
        }

        return possibleCheats.size / 2
    }

    fun part2(input: List<String>): Int {
        var start = -1 to -1
        var end = -1 to -1
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == 'S') {
                    start = x to y
                } else if (input[y][x] == 'E') {
                    end = x to y
                }
            }
        }

        val fairPath = astar(input, start, end)

        val possibleCheats = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (n1 in fairPath) {
            inner@for (n2 in fairPath - n1) {
                if (n1 to n2 in possibleCheats) {
                    continue@inner
                }
                if (heuristic(n1, n2) <= 20) {
                    val saved = astar(input, n1, n2).size - heuristic(n1, n2) - 1 // well that explains why I had to subtract 3 from the size
                    if (saved >= if (input.size > 100) 100 else 50) {
                        possibleCheats.add(n1 to n2)
                        possibleCheats.add(n2 to n1)
                    }
                }
            }
        }

        return possibleCheats.size / 2
    }

    val testInput = readInput("day20_test")

    check(part1(testInput) == 5)
    check(part2(testInput) == 285)

    val input = readInput("day20")
    println(part1(input))
    println(part2(input))
}
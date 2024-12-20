import kotlin.math.abs

fun main() {
    val turns = mapOf(
        -1 to 0 to listOf(0 to 1, 0 to -1),
        1 to 0 to listOf(0 to 1, 0 to -1),
        0 to -1 to listOf(1 to 0, -1 to 0),
        0 to 1 to listOf(1 to 0, -1 to 0),
    )

    fun heuristic(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return abs(end.first - start.first) + abs(end.second - start.second) + 1000
    }

    fun astar(grid: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val openSet = mutableListOf(start to (1 to 0))
        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        val gscore = mutableMapOf(start to 0)
        val fscore = mutableMapOf(start to heuristic(start, end))

        while (openSet.isNotEmpty()) {
            val current = openSet.minBy { fscore[it.first] ?: 10000000 }

            if (current.first == end) {
                return gscore[current.first]!!
            }

            openSet.remove(current)
            for (nextPossible in turns[current.second]!! + current.second) {
                val neighbor = nextPossible.first + current.first.first to nextPossible.second + current.first.second
                if (grid[neighbor.second][neighbor.first] == '#') continue
                val tentativeGScore = (gscore[current.first] ?: 10000000) + if (nextPossible == current.second) 1 else 1001
                if (tentativeGScore < (gscore[neighbor] ?: 10000000)) {
                    cameFrom[neighbor] = current.first
                    gscore[neighbor] = tentativeGScore
                    fscore[neighbor] = tentativeGScore + heuristic(neighbor, end)
                    if (openSet.none { it.first == neighbor }) {
                        openSet.add(neighbor to nextPossible)
                    }
                }
            }
        }

        return -1
    }

    fun astarWithPath(grid: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val openSet = mutableListOf(start to (1 to 0))
        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        val gscore = mutableMapOf(start to 0)
        val fscore = mutableMapOf(start to heuristic(start, end))

        while (openSet.isNotEmpty()) {
            val current = openSet.minBy { fscore[it.first] ?: 10000000 }

            if (current.first == end) {
                val path = mutableSetOf(current.first)
                var thisNode = current.first
                while (thisNode in cameFrom.keys) {
                    thisNode = cameFrom[thisNode]!!
                    path.add(thisNode)
                }
                return path
            }

            openSet.remove(current)
            for (nextPossible in turns[current.second]!! + current.second) {
                val neighbor = nextPossible.first + current.first.first to nextPossible.second + current.first.second
                if (grid[neighbor.second][neighbor.first] == '#') continue
                val tentativeGScore = (gscore[current.first] ?: 10000000) + if (nextPossible == current.second) 1 else 1001
                if (tentativeGScore < (gscore[neighbor] ?: 10000000)) {
                    cameFrom[neighbor] = current.first
                    gscore[neighbor] = tentativeGScore
                    fscore[neighbor] = tentativeGScore + heuristic(neighbor, end)
                    if (openSet.none { it.first == neighbor }) {
                        openSet.add(neighbor to nextPossible)
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

        return astar(input, start, end)
    }

    fun testPossiblePaths(grid: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val cost = astar(grid, start, end)
        val path = astarWithPath(grid, start, end)

        for (point in path) {
            if (point != start && point != end) {
                val newMap = mutableListOf<String>()
                for (y in grid.indices) {
                    val sb = StringBuilder()
                    for (x in grid[0].indices) {
                        if (x to y == point) {
                            sb.append('#')
                        } else {
                            sb.append(grid[y][x])
                        }
                    }
                    newMap.add(sb.toString())
                }

                val newCost = astar(newMap, start, end)
                if (newCost == cost) {
                    return path + testPossiblePaths(newMap, start, end)
                }
            }
        }

        return path
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

       return testPossiblePaths(input, start, end).size
    }

    val testInput = readInput("day16_test")

    check(part1(testInput) == 7036)
    check(part2(testInput) == 45)

    val input = readInput("day16")
    println(part1(input))
    println(part2(input))
}
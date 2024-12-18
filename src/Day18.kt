import kotlin.math.abs

fun main() {
    fun heuristic(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return abs(end.first - start.first) + abs(end.second - start.second)
    }

    fun astar(grid: List<List<Boolean>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Set<Pair<Int, Int>> {
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
                    if (!grid[neighbor.second][neighbor.first]) throw IndexOutOfBoundsException("Wall")
                } catch (ioobe: IndexOutOfBoundsException) { continue }
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
        var grid = mutableListOf<MutableList<Boolean>>()
        if (input.size == 25) { // is test
            for (i in 0..6) {
                grid.add((0..6).map { true }.toMutableList())
            }
        } else {
            for (i in 0..70) {
                grid.add((0..70).map { true }.toMutableList())
            }
        }

        for (byte in input.take(if (input.size == 25) { 12 } else { 1024 })) {
            val point = byte.split(",").map { it.toInt() }
            grid[point[1]][point[0]] = false
        }

//        for (line in grid) {
//            for (point in line) {
//                if (point) print('.') else print('#')
//            }
//            println()
//        }

        return astar(grid, 0 to 0, grid.size - 1 to grid.size - 1).size - 1
    }

    fun part2(input: List<String>): String {
        var grid = mutableListOf<MutableList<Boolean>>()
        if (input.size == 25) { // is test
            for (i in 0..6) {
                grid.add((0..6).map { true }.toMutableList())
            }
        } else {
            for (i in 0..70) {
                grid.add((0..70).map { true }.toMutableList())
            }
        }

        for (byte in input.take(if (input.size == 25) { 12 } else { 1024 })) {
            val point = byte.split(",").map { it.toInt() }
            grid[point[1]][point[0]] = false
        }

        var path = astar(grid, 0 to 0, grid.size - 1 to grid.size - 1)

        for (byte in input.drop(if (input.size == 25) { 12 } else { 1024 })) {
            val point = byte.split(",").map { it.toInt() }
            grid[point[1]][point[0]] = false

            if (point[0] to point[1] in path) {
                path = astar(grid, 0 to 0, grid.size - 1 to grid.size - 1)

                if (path.isEmpty()) {
                    return byte
                }
            }
        }

        return ""
    }

    val testInput = readInput("day18_test")

    check(part1(testInput) == 22)
    check(part2(testInput) == "6,1")

    val input = readInput("day18")
    println(part1(input))
    println(part2(input))
}
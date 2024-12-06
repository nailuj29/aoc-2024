fun main() {
    fun part1(input: List<String>): Int {
        var pos = Pair(-1, -1)
        for (i in input.indices) {
            if (input[i].indexOf('^') != -1) {
                pos = Pair(input[i].indexOf('^'), i)
                break
            }
        }
        var dir = Pair(0, -1)

        val positionsVisited = mutableSetOf(pos)
        while (true) {
            try {
                val newPos = Pair(pos.first + dir.first, pos.second + dir.second)
                if (input[newPos.second][newPos.first] == '#') {
                    dir = when (dir) {
                        Pair(0, -1) -> Pair(1, 0)
                        Pair(1, 0) -> Pair(0, 1)
                        Pair(0, 1) -> Pair(-1, 0)
                        Pair(-1, 0) -> Pair(0, -1)
                        else -> throw Exception("Impossible")
                    }
                    continue
                }
                positionsVisited.add(newPos)
                pos = newPos
            } catch (ioobe: IndexOutOfBoundsException) {
                return positionsVisited.size
            }
        }
    }

    fun canEscape(map: List<String>): Boolean {
        var pos = Pair(-1, -1)
        for (i in map.indices) {
            if (map[i].indexOf('^') != -1) {
                pos = Pair(map[i].indexOf('^'), i)
                break
            }
        }
        var dir = Pair(0, -1)
        val start = pos
        val visited = mutableSetOf(Pair(start, dir))
        while (true) {
            try {
                val newPos = Pair(pos.first + dir.first, pos.second + dir.second)
                if (map[newPos.second][newPos.first] == '#') {
                    dir = when (dir) {
                        Pair(0, -1) -> Pair(1, 0)
                        Pair(1, 0) -> Pair(0, 1)
                        Pair(0, 1) -> Pair(-1, 0)
                        Pair(-1, 0) -> Pair(0, -1)
                        else -> throw Exception("Impossible")
                    }
                    continue
                }
                pos = newPos
                if (visited.contains(Pair(pos, dir))) return false
                visited.add(Pair(pos, dir))
            } catch (ioobe: IndexOutOfBoundsException) {
                return true
            }
        }
    }

    fun part2(input: List<String>): Int {
        var pos = Pair(-1, -1)
        for (i in input.indices) {
            if (input[i].indexOf('^') != -1) {
                pos = Pair(input[i].indexOf('^'), i)
                break
            }
        }
        var dir = Pair(0, -1)

        val start = pos

        val positionsVisited = mutableSetOf(pos)
        while (true) {
            try {
                val newPos = Pair(pos.first + dir.first, pos.second + dir.second)
                if (input[newPos.second][newPos.first] == '#') {
                    dir = when (dir) {
                        Pair(0, -1) -> Pair(1, 0)
                        Pair(1, 0) -> Pair(0, 1)
                        Pair(0, 1) -> Pair(-1, 0)
                        Pair(-1, 0) -> Pair(0, -1)
                        else -> throw Exception("Impossible")
                    }
                    continue
                }

                positionsVisited.add(newPos)
                pos = newPos
            } catch (ioobe: IndexOutOfBoundsException) {
                break
            }
        }

        var count = 0
        for (pos in positionsVisited subtract setOf(start)) {
            val newMap = input.mapIndexed { i, line ->
                line.mapIndexed { j, char ->
                    if (pos == Pair(j, i)) '#' else char
                }.joinToString("")
            }

//            println(newMap.joinToString("\n"))
//            println()

            if (!canEscape(newMap)) count++
        }

        return count
    }

    val testInput = readInput("day6_test")

    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("day6")
    println(part1(input))
    println(part2(input))
}
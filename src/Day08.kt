fun main() {
    fun findAllLocations(grid: List<String>, freq: Char): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == freq) {
                    list.add(Pair(i, j))
                }
            }
        }

        return list
    }

    fun part1(input: List<String>): Int {
        val frequencies = mutableSetOf<Char>()

        for (line in input) {
            for (char in line) {
                if (char != '.') {
                    frequencies.add(char)
                }
            }
        }

        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        for (char in frequencies) {
            val locations = findAllLocations(input, char)

            for (i in locations.indices) {
                for (j in i + 1..<locations.size) {
                    val locationA = locations[i]
                    val locationB = locations[j]

                    val rise = locationA.first - locationB.first
                    val run = locationA.second - locationB.second

                    val antiNodeA = locationA.first + rise to locationA.second + run
                    val antiNodeB = locationB.first - rise to locationB.second - run

                    if (antiNodeA.first in input[0].indices && antiNodeA.second in input.indices) {
                        antiNodes.add(antiNodeA)
                    }

                    if (antiNodeB.first in input[0].indices && antiNodeB.second in input.indices) {
                        antiNodes.add(antiNodeB)
                    }
                }
            }
        }

        return antiNodes.size
    }

    fun part2(input: List<String>): Int {
        val frequencies = mutableSetOf<Char>()

        for (line in input) {
            for (char in line) {
                if (char != '.') {
                    frequencies.add(char)
                }
            }
        }

        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        for (char in frequencies) {
            val locations = findAllLocations(input, char)

            for (i in locations.indices) {
                for (j in i + 1..<locations.size) {
                    val locationA = locations[i]
                    val locationB = locations[j]

                    antiNodes.add(locationA)
                    antiNodes.add(locationB)


                    val rise = locationA.first - locationB.first
                    val run = locationA.second - locationB.second

                    var antiNodeA = locationA.first + rise to locationA.second + run
                    var antiNodeB = locationB.first - rise to locationB.second - run

                    while (antiNodeA.first in input[0].indices && antiNodeA.second in input.indices) {
                        antiNodes.add(antiNodeA)

                        antiNodeA = antiNodeA.first + rise to antiNodeA.second + run
                    }

                    while (antiNodeB.first in input[0].indices && antiNodeB.second in input.indices) {
                        antiNodes.add(antiNodeB)

                        antiNodeB = antiNodeB.first - rise to antiNodeB.second - run
                    }
                }
            }
        }

        return antiNodes.size
    }

    val testInput = readInput("day8_test")

    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("day8")
    println(part1(input))
    println(part2(input))
}
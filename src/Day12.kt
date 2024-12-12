data class RegionDB(val regions: MutableList<Set<Pair<Int, Int>>>) {
    fun contains(pair: Pair<Int, Int>): Boolean {
        return regions.any { region -> region.contains(pair) }
    }
}

fun main() {
    fun findOthers(map: List<String>, point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val regionType = map[point.first][point.second]
        val set = mutableSetOf<Pair<Int, Int>>()

        fun inner(point: Pair<Int, Int>) {
            if (set.contains(point)) { return }
            try {
                if (map[point.first][point.second] != regionType) {
                    return
                }
            } catch (ioobe: IndexOutOfBoundsException) {
                return
            }

            set.add(point)

            inner(point.first to point.second + 1)
            inner(point.first to point.second - 1)
            inner(point.first + 1 to point.second)
            inner(point.first - 1 to point.second)
        }

        inner(point)

        return set
    }

    fun part1(input: List<String>): Int {
        val db = RegionDB(mutableListOf())

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (!db.contains(i to j)) {
                    db.regions.add(findOthers(input, i to j))
                }
            }
        }

        var sum = 0
        for (region in db.regions) {
            val area = region.size
            var perimeter = 0

            for (point in region) {
                perimeter += 4
                if (region.contains(point.first to point.second + 1)) {
                    perimeter--
                }
                if (region.contains(point.first to point.second - 1)) {
                    perimeter--
                }
                if (region.contains(point.first + 1 to point.second)) {
                    perimeter--
                }
                if (region.contains(point.first - 1 to point.second)) {
                    perimeter--
                }
            }

            sum += area * perimeter
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val db = RegionDB(mutableListOf())

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (!db.contains(i to j)) {
                    db.regions.add(findOthers(input, i to j))
                }
            }
        }

        var sum = 0
        for (region in db.regions) {
            val area = region.size

            var numSides = 0
            val xMin = region.minOf { it.second } - 1
            val xMax = region.maxOf { it.second } + 1
            val yMin = region.minOf { it.first } - 1
            val yMax = region.maxOf { it.first } + 1

            var firstIter = true

            var prevEntry = setOf<Int>()
            var prevExit = setOf<Int>()
            var thisEntry = mutableSetOf<Int>()
            var thisExit = mutableSetOf<Int>()
            for (y in yMin..yMax) {
                var inside = false
                for (x in xMin..xMax) {
                    if (region.contains(y to x)) {
                        if (!inside) {
                            thisEntry.add(x)
                            inside = true
                            if (firstIter) {
                                firstIter = false
                            }
                        }
                    } else {
                        if (inside) {
                            thisExit.add(x)
                            inside = false
                        }
                    }
                }

                if (!firstIter) {
                    if (thisExit.size > prevExit.size) {
                        numSides += (thisExit - prevExit).size
                    } else {
                        numSides += (thisExit - prevExit).size
                    }

                    if (thisEntry.size > prevEntry.size) {
                        numSides += (thisEntry - prevEntry).size
                    } else {
                        numSides += (thisEntry - prevEntry).size
                    }
                }

                prevExit = thisExit
                prevEntry = thisEntry
                thisExit = mutableSetOf()
                thisEntry = mutableSetOf()
            }

            firstIter = true

            prevEntry = setOf()
            thisExit = mutableSetOf()
            for (x in xMin..xMax) {
                var inside = false
                for (y in yMin..yMax) {
                    if (region.contains(y to x)) {
                        if (!inside) {
                            thisEntry.add(y)
                            inside = true
                            if (firstIter) {
                                firstIter = false
                            }
                        }
                    } else {
                        if (inside) {
                            thisExit.add(y)
                            inside = false
                        }
                    }
                }

                if (!firstIter) {
                    if (thisExit.size > prevExit.size) {
                        numSides += (thisExit - prevExit).size
                    } else {
                        numSides += (thisExit - prevExit).size
                    }
                    if (thisEntry.size > prevEntry.size) {
                        numSides += (thisEntry - prevEntry).size
                    } else {
                        numSides += (thisEntry - prevEntry).size
                    }
                }

                prevEntry = thisEntry
                prevExit = thisExit
                thisEntry = mutableSetOf()
                thisExit = mutableSetOf()
            }

            sum += area * numSides
        }

        return sum
    }

    val testInput = readInput("day12_test")

    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    val input = readInput("day12")
    println(part1(input))
    println(part2(input))
}
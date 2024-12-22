fun main() {
    val numberPad = mapOf(
        '7' to (0 to 0), '8' to (1 to 0), '9' to (2 to 0),
        '4' to (0 to 1), '5' to (1 to 1), '6' to (2 to 1),
        '1' to (0 to 2), '2' to (1 to 2), '3' to (2 to 2),
                         '0' to (1 to 3), 'A' to (2 to 3)
    )

    val directionPad = mapOf(
                         '^' to (1 to 0), 'A' to (2 to 0),
        '<' to (0 to 1), 'v' to (1 to 1), '>' to (2 to 1),
    )

    fun simulate(buttonPresses: String, buttonMap: Map<Char, Pair<Int, Int>>, startPos: Pair<Int, Int>): String {
        val reversedMap = mutableMapOf<Pair<Int, Int>, Char>()
        for (key in buttonMap.keys) {
            reversedMap[buttonMap[key]!!] = key
        }

        var pos = startPos
        val sb = StringBuilder()
        for (press in buttonPresses) {
            when (press) {
                '^' -> pos = pos.first to pos.second - 1
                'v' -> pos = pos.first to pos.second + 1
                '<' -> pos = pos.first - 1 to pos.second
                '>' -> pos = pos.first + 1 to pos.second
                'A' -> sb.append(reversedMap[pos]!!)
            }

            check(reversedMap[pos] != null)
        }

        return sb.toString()
    }

    val pressCountCache = mutableMapOf<Pair<String, Int>, Long>()
    fun getPressCount(presses: String, depth: Int): Long {
        if (pressCountCache.containsKey(presses to depth)) {
            return pressCountCache[presses to depth]!!
        }

        if (depth == 0) {
            pressCountCache[presses to 0] = presses.length.toLong()
            return presses.length.toLong()
        }

        if (presses.contains("\\d".toRegex())) {
            var pos = 2 to 3
            var sum = 0L
            for (press in presses) {
                val nextPos = numberPad[press]!!
                val dx = nextPos.first - pos.first
                val dy = nextPos.second - pos.second
                val xMoves = StringBuilder()
                if (dx < 0) {
                    for (i in 1..-dx) {
                        xMoves.append('<')
                    }
                }

                if (dx > 0) {
                    for (i in 1..dx) {
                        xMoves.append('>')
                    }
                }

                val yMoves = StringBuilder()
                if (dy < 0) {
                    for (i in 1..-dy) {
                        yMoves.append('^')
                    }
                }

                if (dy > 0) {
                    for (i in 1..dy) {
                        yMoves.append('v')
                    }
                }

                val nextMoveSeqs = mutableSetOf<String>()
                if (numberPad.containsValue(nextPos.first to pos.second)) { // no empty space after moving in xdir
                    nextMoveSeqs.add(xMoves.toString() + yMoves.toString() + 'A')
                }
                if (numberPad.containsValue(pos.first to nextPos.second)) { // no empty space after moving in ydir
                    nextMoveSeqs.add(yMoves.toString() + xMoves.toString() + 'A')
                }

                check(nextMoveSeqs.size > 0)

                pos = nextPos
                sum += nextMoveSeqs.minOfOrNull {
                    getPressCount(it, depth - 1)
                }!!
            }

            pressCountCache[presses to depth] = sum
            return sum
        } else {
            var pos = 2 to 0
            var sum = 0L
            for (press in presses) {
                val nextPos = directionPad[press]!!
                val dx = nextPos.first - pos.first
                val dy = nextPos.second - pos.second
                val xMoves = StringBuilder()
                if (dx < 0) {
                    for (i in 1..-dx) {
                        xMoves.append('<')
                    }
                }

                if (dx > 0) {
                    for (i in 1..dx) {
                        xMoves.append('>')
                    }
                }

                val yMoves = StringBuilder()
                if (dy < 0) {
                    for (i in 1..-dy) {
                        yMoves.append('^')
                    }
                }

                if (dy > 0) {
                    for (i in 1..dy) {
                        yMoves.append('v')
                    }
                }

                val nextMoveSeqs = mutableSetOf<String>()
                if (directionPad.containsValue(nextPos.first to pos.second)) { // no empty space after moving in xdir
                    nextMoveSeqs.add(xMoves.toString() + yMoves.toString() + 'A')
                }
                if (directionPad.containsValue(pos.first to nextPos.second)) { // no empty space after moving in ydir
                    nextMoveSeqs.add(yMoves.toString() + xMoves.toString() + 'A')
                }

                check(nextMoveSeqs.size > 0)

                pos = nextPos
                sum += nextMoveSeqs.minOfOrNull {
                    getPressCount(it, depth - 1)
                }!!
            }

            pressCountCache[presses to depth] = sum
            return sum
        }
    }

    fun part1(input: List<String>): Long {
        var count = 0L
        for (code in input) {
            count += code.substring(0, 3).toInt() * getPressCount(code, 3)
        }

        return count
    }

    fun part2(input: List<String>): Long {
        var count = 0L
        for (code in input) {
            count += code.substring(0, 3).toInt() * getPressCount(code, 26)
        }

        return count
    }

    val testInput = readInput("day21_test")

    check(part1(testInput) == 126384L)

    val input = readInput("day21")
    println(part1(input))
    println(part2(input))
}
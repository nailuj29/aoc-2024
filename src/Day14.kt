import kotlin.math.floor

data class Robot(var position: Pair<Int, Int>, val velocity: Pair<Int, Int>)

fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
        val robots = mutableListOf<Robot>()
        val width = if (input.size < 100) { 11 } else { 101 }
        val height = if (input.size < 100) { 7 } else { 103 }

        for (line in input) {
            val matches = regex.find(line)!!.groupValues
            robots.add(Robot(
                matches[1].toInt() to matches[2].toInt(),
                matches[3].toInt() to matches[4].toInt(),
            ))
        }

        for (i in 1..100) {
            for (robot in robots) {
                robot.position = Pair(
                    (robot.position.first + robot.velocity.first + width) % width,
                    (robot.position.second + robot.velocity.second + height) % height)
            }
        }

        val quadrant1 = robots.filter {
            it.position.first > floor(width / 2.0) && it.position.second < floor(height / 2.0)
        }

        val quadrant2 = robots.filter {
            it.position.first > floor(width / 2.0) && it.position.second > floor(height / 2.0)
        }

        val quadrant3 = robots.filter {
            it.position.first < floor(width / 2.0) && it.position.second > floor(height / 2.0)
        }

        val quadrant4 = robots.filter {
            it.position.first < floor(width / 2.0) && it.position.second < floor(height / 2.0)
        }

        return listOf(quadrant1, quadrant2, quadrant3, quadrant4).map { it.size }.fold(1, Int::times)
    }

    fun findRow(length: Int, robots: List<Robot>): Boolean {
        val positions = robots.map {
            it.position
        }.toSet()

        outer@for (robot in positions) {
            for (x in robot.first..<robot.first+length) {
                if (!positions.contains(x to robot.second)) {
                    continue@outer
                }
            }
            return true
        }

        return false
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
        val robots = mutableListOf<Robot>()
        val width = if (input.size < 100) { 11 } else { 101 }
        val height = if (input.size < 100) { 7 } else { 103 }

        for (line in input) {
            val matches = regex.find(line)!!.groupValues
            robots.add(Robot(
                matches[1].toInt() to matches[2].toInt(),
                matches[3].toInt() to matches[4].toInt(),
            ))
        }

        var i = 0
        while (!findRow(20, robots)) {
            for (robot in robots) {
                robot.position = Pair(
                    (robot.position.first + robot.velocity.first + width) % width,
                    (robot.position.second + robot.velocity.second + height) % height)
            }
            i++
        }

        for (y in 0..<height) {
            for (x in 0..<width) {
                val count = robots.count { it.position == x to y  }

                if (count == 0) {
                    print('.')
                } else {
                    print('*')
                }
            }
            println()
        }

        return i
    }

    val testInput = readInput("day14_test")

    check(part1(testInput) == 12)

    val input = readInput("day14")
    println(part1(input))
    println(part2(input))
}
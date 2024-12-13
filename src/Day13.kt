data class Machine(val a: Pair<Int, Int>, val b: Pair<Int, Int>, val prize: Pair<Long, Long>)

// Logic for this is in Day13Math.pdf

fun main() {
    fun part1(input: List<String>): Int {
        val raw = input.joinToString("\n")
        val machineRaw = raw.split("\n\n").map { it.split("\n") }
        val machines = mutableListOf<Machine>()
        val twoNumsRegex = Regex("(\\d+).*?(\\d+)")
        for (machine in machineRaw) {
            val aButtonMatches = twoNumsRegex.find(machine[0])
            val bButtonMatches = twoNumsRegex.find(machine[1])
            val prizeMatches = twoNumsRegex.find(machine[2])

            machines.add(Machine(
                aButtonMatches!!.groupValues[1].toInt() to aButtonMatches.groupValues[2].toInt(),
                bButtonMatches!!.groupValues[1].toInt() to bButtonMatches.groupValues[2].toInt(),
                prizeMatches!!.groupValues[1].toLong() to prizeMatches.groupValues[2].toLong(),
            ))
        }

        var total = 0

        for (machine in machines) {
            val possibleCosts = mutableListOf<Long>()
            for (b in 0..100) {
                val x_nbxb = machine.prize.first - b * machine.b.first
                val y_nbyb = machine.prize.second - b * machine.b.second

                if (x_nbxb < 0) continue // Will result in negative A value
                if (y_nbyb < 0) continue

                if (x_nbxb % machine.a.first == 0L && y_nbyb % machine.a.second == 0L && (x_nbxb / machine.a.first) <= 100) {
                    check(b * machine.b.first + (x_nbxb / machine.a.first) * machine.a.first == machine.prize.first)
                    check(b * machine.b.second + (y_nbyb / machine.a.second) * machine.a.second == machine.prize.second)
                    if (y_nbyb / machine.a.second != x_nbxb / machine.a.first) {
                        continue
                    }
                    possibleCosts += 3 * (x_nbxb / machine.a.first) + b
                }
            }

            if (possibleCosts.isNotEmpty()) {
                total += possibleCosts.min().toInt()
            }
        }

        return total
    }

    fun part2(input: List<String>): Long {
        val raw = input.joinToString("\n")
        val machineRaw = raw.split("\n\n").map { it.split("\n") }
        val machines = mutableListOf<Machine>()
        val twoNumsRegex = Regex("(\\d+).*?(\\d+)")
        for (machine in machineRaw) {
            val aButtonMatches = twoNumsRegex.find(machine[0])
            val bButtonMatches = twoNumsRegex.find(machine[1])
            val prizeMatches = twoNumsRegex.find(machine[2])

            machines.add(Machine(
                aButtonMatches!!.groupValues[1].toInt() to aButtonMatches.groupValues[2].toInt(),
                bButtonMatches!!.groupValues[1].toInt() to bButtonMatches.groupValues[2].toInt(),
                prizeMatches!!.groupValues[1].toLong() + 10000000000000
                        to prizeMatches.groupValues[2].toLong() + 10000000000000,
            ))
        }

        var total = 0L

        for (machine in machines) {
            val b = (machine.a.second * machine.prize.first - machine.a.first * machine.prize.second) /
                    (machine.b.first * machine.a.second - machine.a.first * machine.b.second)

            val a = (machine.prize.first - b * machine.b.first) / machine.a.first

            if ((machine.prize.first - b * machine.b.first) % machine.a.first == 0L &&
                (machine.prize.second - b * machine.b.second) % machine.a.second == 0L) {
                total += 3 * a + b
            }
        }

        return total
    }

    val testInput = readInput("day13_test")

    check(part1(testInput) == 480)

    val input = readInput("day13")
    println(part1(input))
    println(part2(input))
}
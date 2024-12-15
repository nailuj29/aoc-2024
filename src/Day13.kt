data class Machine(val a: Pair<Int, Int>, val b: Pair<Int, Int>, val prize: Pair<Long, Long>)

// Logic for this is in Day13Math.pdf

fun main() {
    fun solution(input: List<String>, offset: Long): Long {
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
                prizeMatches!!.groupValues[1].toLong() + offset
                        to prizeMatches.groupValues[2].toLong() + offset,
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

    check(solution(testInput, 0) == 480L)

    val input = readInput("day13")
    println(solution(input, 0))
    println(solution(input, 10000000000000))
}
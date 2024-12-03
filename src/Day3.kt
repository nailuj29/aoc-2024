fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")

        val string = input.joinToString("")
        val matches = regex.findAll(string)

        var count = 0
        for (match in matches) {
            count += match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val doDontRegex = Regex("don't\\(\\).*?do\\(\\)")

        var string = input.joinToString("")

        val disabledMatches = doDontRegex.findAll(string)
        for (match in disabledMatches) {
            string = string.replace(match.groupValues[0], "")
        }

        return part1(listOf(string))
    }

    val testInput = readInput("day3_test")

    check(part1(testInput) == 161)

    val testInput2 = readInput("day3_test2")

    check(part2(testInput2) == 48)

    val input = readInput("day3")
    println(part1(input))
    println(part2(input))
}
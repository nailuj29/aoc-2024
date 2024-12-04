fun main() {
    fun part1(input: List<String>): Int {
        var count = 0

        val xmasRegex = Regex("XMAS")
        val samxRegex = Regex("SAMX")

        for (line in input) {
            count += xmasRegex.findAll(line).count()
            count += samxRegex.findAll(line).count()
        }

        val transposed = mutableListOf<String>()
        for (i in input[0].indices) {
            val sb = StringBuilder()
            for (j in input.indices) {
                sb.append(input[j][i])
            }

            transposed.add(sb.toString())
        }

        for (line in transposed) {
            count += xmasRegex.findAll(line).count()
            count += samxRegex.findAll(line).count()
        }

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == 'X') {
                    // UpLeft
                    try {
                        if (input[i-1][j-1] == 'M' &&
                            input[i-2][j-2] == 'A' &&
                            input[i-3][j-3] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // UpRight
                    try {
                        if (input[i-1][j+1] == 'M' &&
                            input[i-2][j+2] == 'A' &&
                            input[i-3][j+3] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // DownLeft
                    try {
                        if (input[i+1][j-1] == 'M' &&
                            input[i+2][j-2] == 'A' &&
                            input[i+3][j-3] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // DownRight
                    try {
                        if (input[i+1][j+1] == 'M' &&
                            input[i+2][j+2] == 'A' &&
                            input[i+3][j+3] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == 'A') {
                    // M M
                    //  A
                    // S S
                    try {
                        if (input[i-1][j-1] == 'M' &&
                            input[i-1][j+1] == 'M' &&
                            input[i+1][j+1] == 'S' &&
                            input[i+1][j-1] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // M S
                    //  A
                    // M S
                    try {
                        if (input[i-1][j-1] == 'M' &&
                            input[i+1][j-1] == 'M' &&
                            input[i+1][j+1] == 'S' &&
                            input[i-1][j+1] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // S M
                    //  A
                    // S M
                    try {
                        if (input[i-1][j+1] == 'M' &&
                            input[i+1][j+1] == 'M' &&
                            input[i-1][j-1] == 'S' &&
                            input[i+1][j-1] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}

                    // S S
                    //  A
                    // M M
                    try {
                        if (input[i+1][j-1] == 'M' &&
                            input[i+1][j+1] == 'M' &&
                            input[i-1][j+1] == 'S' &&
                            input[i-1][j-1] == 'S') {
                            count++
                        }
                    } catch (iobe: IndexOutOfBoundsException) {}
                }
            }
        }

        return count
    }

    val testInput = readInput("day4_test")

    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("day4")
    println(part1(input))
    println(part2(input))
}
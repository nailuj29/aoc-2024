fun main() {
    fun part1(input: List<String>): Long {
        val diskMap = input[0]
        var idNum = 0
        val layout = mutableListOf<Int>()

        for (i in diskMap.indices.filter { it % 2 == 0 }) {
            for (j in 1..diskMap[i].toString().toInt()) {
                layout.add(idNum)
            }
            if (i + 1 != diskMap.length) {
                for (j in 1..diskMap[i + 1].toString().toInt()) {
                    layout.add(-1)
                }
            }
            idNum++
        }

        while (layout.contains(-1)) {
            val num = layout.removeLast()
            if (num != -1) {
                layout[layout.indexOf(-1)] = num
            }
        }

        var checkSum = 0L
        for (i in layout.indices) {
            checkSum += layout[i] * i
        }

        return checkSum
    }

    fun part2(input: List<String>): Long {
        val diskMap = input[0]
        var idNum = 0
        val layout = mutableListOf<Int>()

        for (i in diskMap.indices.filter { it % 2 == 0 }) {
            for (j in 1..diskMap[i].toString().toInt()) {
                layout.add(idNum)
            }
            if (i + 1 != diskMap.length) {
                for (j in 1..diskMap[i + 1].toString().toInt()) {
                    layout.add(-1)
                }
            }
            idNum++
        }

        for (i in (idNum - 1) downTo 0) {
            val startIdx = layout.indexOf(i)
            val endIdx = layout.lastIndexOf(i)

            val size = endIdx - startIdx + 1
            var succeeded = false
            outer@for ((j, chunk) in layout.windowed(size).withIndex()) {
                if (j > startIdx) {
                    break@outer
                }
                if (chunk.size != size) {
                    continue@outer
                }
                if (chunk.all { it == -1 }) {
                    for (k in j..<(j + size)) {
                        layout[k] = i
                    }
                    succeeded = true
                    break@outer
                }
            }

            if (succeeded) {
                for (j in startIdx..endIdx) {
                    layout[j] = -1
                }
            }
        }

        var checkSum = 0L
        for (i in layout.indices) {
            if (layout[i] != -1) {
                checkSum += layout[i] * i
            }
        }

        return checkSum
    }

    val testInput = readInput("day9_test")

    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("day9")
    println(part1(input))
    println(part2(input))
}
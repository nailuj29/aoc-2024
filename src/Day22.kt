fun main() {
    fun mix(secNum: Long, num: Long): Long {
        return secNum xor num
    }

    fun prune(num: Long): Long {
        return num % 16777216
    }

    fun nextSec(secNum: Long): Long {
        val step1 = prune(
            mix(secNum, secNum * 64)
        )

        val step2 = prune(
            mix(step1, step1 / 32)
        )

        val step3 = prune(
            mix(step2, step2 * 2048)
        )

        return step3
    }

    fun part1(input: List<String>): Long {
        var sum = 0L

        for (num in input) {
            var sec = num.toLong()
            for (i in 1..2000) {
                sec = nextSec(sec)
            }

            sum += sec
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val priceChangeValues = mutableMapOf<List<Int>, Int>()

        for (num in input) {
            var sec = num.toLong()
            val sequence = mutableListOf(sec)
            for (i in 1..2000) {
                sec = nextSec(sec)
                sequence.add(sec)
            }

            val foundChanges = mutableSetOf<List<Int>>()
            inner@for (fiveVals in sequence.windowed(5)) {
                val onesDigits = fiveVals.map {
                    (it - (it / 10) * 10).toInt()
                }

                val priceChanges = mutableListOf<Int>()
                for (prevAndNext in onesDigits.windowed(2)) {
                    priceChanges.add(prevAndNext[1] - prevAndNext[0])
                }

                if (foundChanges.contains(priceChanges)) {
                    continue@inner
                }
                val price = onesDigits[4]
                priceChangeValues[priceChanges] = (priceChangeValues[priceChanges] ?: 0) + price
                foundChanges.add(priceChanges)
            }

        }

        return priceChangeValues.values.max()
    }
//
//    var sec = nextSec(2)
//    var prev = 2L
//    println(prev.toString().padStart(8))
//    for (i in 1..2000) {
//        val price = sec - sec / 10 * 10
//        println("${sec.toString().padStart(8)}: $price (${price - prev})")
//        prev = price
//        sec = nextSec(sec)
//    }


    val testInput = readInput("day22_test")

    check(part1(testInput) == 37327623L)

    val testInput2 = readInput("day22_test2")

    check(part2(testInput2) == 23)

    val input = readInput("day22")
    println(part1(input))
    println(part2(input))
}
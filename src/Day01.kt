import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (line in input) {
            val nums = line.split("   ").map { it.toInt() }

            list1.add(nums[0])
            list2.add(nums[1])
        }

        list1.sort()
        list2.sort()

        var sum = 0
        for (i in list1.indices) {
            sum += abs(list1[i] - list2[i])
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (line in input) {
            val nums = line.split("   ").map { it.toInt() }

            list1.add(nums[0])
            list2.add(nums[1])
        }

        var sum = 0
        for (i in list1) {
            sum += i * list2.count { it == i }
        }

        return sum
    }

    val testInput = readInput("day1_test")

    check(part1(testInput) == 11)
     check(part2(testInput) == 31)

    val input = readInput("day1")
    println(part1(input))
    println(part2(input))
}
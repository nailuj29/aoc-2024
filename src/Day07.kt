fun main() {
    fun canReachTarget1(target: Long, nums: List<Long>): Boolean {
        if (nums.isEmpty()) { return false }
        if (nums.size == 1) {
            return nums[0] == target
        }

        else {
            return canReachTarget1(target, listOf(nums[0] + nums[1]) + nums.drop(2)) ||
                    canReachTarget1(target, listOf(nums[0] * nums[1]) + nums.drop(2))
        }
    }

    fun canReachTarget2(target: Long, nums: List<Long>): Boolean {
        if (nums.isEmpty()) { return false }
        if (nums.size == 1) {
            return nums[0] == target
        }

        else {
            return canReachTarget2(target, listOf(nums[0] + nums[1]) + nums.drop(2)) ||
                    canReachTarget2(target, listOf(nums[0] * nums[1]) + nums.drop(2)) ||
                    canReachTarget2(target, listOf((nums[0].toString() + nums[1]).toLong()) + nums.drop(2))
        }
    }

    fun part1(input: List<String>): Long {
        var count = 0L

        for (line in input) {
            val parts = line.split(": ")
            val target = parts[0].toLong()

            val nums = parts[1].split(" ").map { it.toLong() }

            if (canReachTarget1(target, nums)) {
                count += target
            }
        }

        return count
    }

    fun part2(input: List<String>): Long {
        var count = 0L

        for (line in input) {
            val parts = line.split(": ")
            val target = parts[0].toLong()

            val nums = parts[1].split(" ").map { it.toLong() }

            if (canReachTarget2(target, nums)) {
                count += target
            }
        }

        return count
    }

    val testInput = readInput("day7_test")

    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("day7")
    println(part1(input))
    println(part2(input))
}
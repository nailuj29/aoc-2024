enum class ThingType {
    ROBOT, WALL, BOX, BOX_LEFT, BOX_RIGHT
}

data class Thing(var pos: Pair<Int, Int>, val type: ThingType) {
    fun canMove(otherThings: List<Thing>, dir: Pair<Int, Int>, otherHalfChecked: Boolean): Boolean {
        if (this.type == ThingType.WALL) return false

        if (this.type == ThingType.BOX_LEFT && !otherHalfChecked && dir.second != 0) {
            val rightHalf = otherThings.filter { it.pos == this.pos.first + 1 to this.pos.second }[0]
            if (!rightHalf.canMove(otherThings, dir, true)) return false
        }

        if (this.type == ThingType.BOX_RIGHT && !otherHalfChecked && dir.second != 0) {
            val leftHalf = otherThings.filter { it.pos == this.pos.first - 1 to this.pos.second }[0]
            if (!leftHalf.canMove(otherThings, dir, true)) return false
        }

        val target = pos.first + dir.first to pos.second + dir.second
        for (thing in otherThings) {
            if (thing.pos == target) {
                return thing.canMove(otherThings, dir, false)
            }
        }

        return true
    }

    fun move(otherThings: List<Thing>, dir: Pair<Int, Int>): Boolean {
        return move(otherThings, dir, false)
    }

    fun move(otherThings: List<Thing>, dir: Pair<Int, Int>, otherHalfMoved: Boolean): Boolean {
        if (!this.canMove(otherThings, dir, false)) return false

        if (this.type == ThingType.BOX_LEFT && dir.second != 0 && !otherHalfMoved) {
            val rightHalf = otherThings.filter { it.pos == this.pos.first + 1 to this.pos.second }[0]
            if (!rightHalf.move(otherThings, dir, true)) return false
        }

        if (this.type == ThingType.BOX_RIGHT && dir.second != 0 && !otherHalfMoved) {
            val leftHalf = otherThings.filter { it.pos == this.pos.first - 1 to this.pos.second }[0]
            if (!leftHalf.move(otherThings, dir, true)) return false
        }

        val target = pos.first + dir.first to pos.second + dir.second
        for (thing in otherThings) {
            if (thing.pos == target) {
                if (thing.move(otherThings, dir)) {
                    this.pos = target
                    return true
                } else {
                    return false
                }
            }
        }

        this.pos = target
        return true
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val raw = input.joinToString("\n")
        val parts = raw.split("\n\n")

        val map = parts[0].split("\n")
        val moves = parts[1]

        val things = mutableListOf<Thing>()
        var robot: Thing? = null
        for (y in map.indices) {
            for (x in map[0].indices) {
                when (map[y][x]) {
                    '#' -> things.add(Thing(x to y, ThingType.WALL))
                    'O' -> things.add(Thing(x to y, ThingType.BOX))
                    '@' -> {
                        robot = Thing(x to y, ThingType.ROBOT)
                        things.add(robot)
                    }
                }
            }
        }

        check(robot != null)
        for (move in moves) {
            robot.move(
                things, when (move) {
                    '>' -> 1 to 0
                    '<' -> -1 to 0
                    '^' -> 0 to -1
                    'v' -> 0 to 1
                    else -> throw IllegalStateException("Invalid move")
                }
            )
        }

        val boxes = things.filter { it.type == ThingType.BOX }

        var count = 0

        for (box in boxes) {
            count += box.pos.first + 100 * box.pos.second
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val raw = input.joinToString("\n")
        val parts = raw.split("\n\n")

        val map = parts[0].split("\n")
        val moves = parts[1]

        val things = mutableListOf<Thing>()
        var robot: Thing? = null
        for (y in map.indices) {
            for (x in map[0].indices) {
                when (map[y][x]) {
                    '#' -> {
                        things.add(Thing(x * 2 to y, ThingType.WALL))
                        things.add(Thing(x * 2 + 1 to y, ThingType.WALL))
                    }
                    'O' -> {
                        things.add(Thing(x * 2 to y, ThingType.BOX_LEFT))
                        things.add(Thing(x * 2 + 1 to y, ThingType.BOX_RIGHT))
                    }
                    '@' -> {
                        robot = Thing(x * 2 to y, ThingType.ROBOT)
                        things.add(robot!!)
                    }
                }
            }
        }

        check(robot != null)
        for (move in moves) {
            robot!!.move(
                things, when (move) {
                    '>' -> 1 to 0
                    '<' -> -1 to 0
                    '^' -> 0 to -1
                    'v' -> 0 to 1
                    else -> throw IllegalStateException("Invalid move")
                }
            )
        }

        var count = 0

        val leftBoxes = things.filter { it.type == ThingType.BOX_LEFT }.map { it.pos }

        for (y in map.indices) {
            for (x in 0..map[0].length * 2) {
                if (leftBoxes.contains(Pair(x, y))) {
                    count += y * 100 + x
                }
            }
        }

        return count
    }

    val testInput = readInput("day15_test")

    check(part1(testInput) == 2028)

    val testInput2 = readInput("day15_test2")
    check(part2(testInput2) == 9021)

    val input = readInput("day15")
    println(part1(input))
    println(part2(input))
}
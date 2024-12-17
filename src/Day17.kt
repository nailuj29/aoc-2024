import kotlin.math.pow

fun main() {
    fun runProgram(
        aReg: Long,
        bReg: Long,
        cReg: Long,
        program: List<Int>
    ): List<Int> {
        if (aReg == 0L) {
            return listOf()
        }
        var a = aReg
        var b = bReg
        var c = cReg
        val output = mutableListOf<Int>()

        fun comboOperand(op: Int): Long {
            check(op in 0..6)
            if (op <= 3) {
                return op.toLong()
            }

            if (op == 4) {
                return a
            }

            if (op == 5) {
                return b
            }

            if (op == 6) {
                return c
            }

            throw IllegalStateException("Unreachable")
        }

        var i = 0
        while (i < program.size) {
            val instr = program[i]
            val operand = program[i + 1]
            when (instr) {
                0 -> { // adv
                    a /= 2.0.pow(comboOperand(operand).toDouble()).toLong()
                }

                1 -> { // bxl
                    b = b xor operand.toLong()
                }

                2 -> { // bst
                    b = comboOperand(operand) % 8
                }

                3 -> { // jnz
                    if (a != 0L) {
                        i = operand - 2
                    }
                }

                4 -> { // bxc
                    b = b xor c
                }

                5 -> { // out
                    output.add((comboOperand(operand) % 8).toInt())
                }

                6 -> { // bdv
                    b = a / 2.0.pow(comboOperand(operand).toDouble()).toLong()
                }

                7 -> { // cdv
                    c = a / 2.0.pow(comboOperand(operand).toDouble()).toLong()
                }
            }
            i += 2
        }

        return output
    }

    fun part1(input: List<String>): String {
        val aReg = input[0].drop(12).toInt()
        val bReg = input[1].drop(12).toInt()
        val cReg = input[2].drop(12).toInt()

        val program = input[4].drop(9).split(",").map { it.toInt() }

        return runProgram(aReg.toLong(), bReg.toLong(), cReg.toLong(), program).joinToString(",")
    }

    fun search(program: List<Int>, aReg: Long, n: Int): Long {
        if (n > program.size) return aReg
        for (i in 0..7) {
            val a = aReg * 8 + i
            val result = runProgram(a, 0, 0, program)

            if (result == program.takeLast(n)) {
                val nextA = search(program, a, n + 1)
                if (nextA != -1L) {
                    return nextA
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Long {
        val program = input[4].drop(9).split(",").map { it.toInt() }

        val res = search(program, 0, 1)
        return res
    }

    val testInput = readInput("day17_test")

    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")

    val testInput2 = readInput("day17_test2")
    check(part2(testInput2) == 117440L)

    val input = readInput("day17")
    println(part1(input))
    println(part2(input))
}
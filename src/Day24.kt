fun main() {
    fun findValue(value: String, rules: List<Pair<String, String>>, known: MutableMap<String, Boolean>): Boolean {
        if (known.containsKey(value)) {
            return known[value]!!
        }

        val relevantRule = rules.filter { rule -> rule.second == value }[0]
        val parts = relevantRule.first.split(" ")
        val lhs = parts[0]
        val op = parts[1]
        val rhs = parts[2]

        when (op) {
            "AND" -> {
                known[value] = findValue(lhs, rules, known) && findValue(rhs, rules, known)
                return known[value]!!
            }

            "OR" -> {
                known[value] = findValue(lhs, rules, known) || findValue(rhs, rules, known)
                return known[value]!!
            }

            "XOR" -> {
                known[value] = findValue(lhs, rules, known) xor findValue(rhs, rules, known)
                return known[value]!!
            }
        }

        return false
    }

    fun part1(input: List<String>): Long {
        val raw = input.joinToString("\n").split("\n\n")
        val starting = raw[0].split("\n").map {
            val split = it.split(": ")
            split[0] to (split[1].toInt() == 1)
        }

        val rules = raw[1].split("\n").map {
            val split = it.split(" -> ")
            split[0] to split[1]
        }

        val known = mutableMapOf<String, Boolean>()
        for (start in starting) {
            known[start.first] = start.second
        }

        val zVals = rules.filter { it.second.startsWith("z") }.map { it.second }.sorted().reversed()

        for (z in zVals) {
            findValue(z, rules, known)
        }

        return zVals.joinToString("") {
            if (known[it]!!) "1" else "0"
        }.toLong(2)
    }

    fun part2(input: List<String>): String {
        val raw = input.joinToString("\n").split("\n\n")

        val rules = raw[1].split("\n").map {
            val split = it.split(" -> ")
            split[0] to split[1]
        }

        val bad = mutableSetOf<String>()

        val xors = rules.filter {
            it.first.contains("XOR")
        }

        val xorInputs = xors.map { it.first }

        for (xor in xors) {
            if (!xor.first.matches("[xy]\\d{2} XOR [xy]\\d{2}".toRegex()) && !xor.second.matches("z\\d{2}".toRegex())) {
                bad.add(xor.second)
            }

            if (!xor.first.matches("[xy]00 XOR [xy]00".toRegex())) {
                if (!xorInputs.any { it.contains(xor.second) } && !xor.second.startsWith("z")) {
                    bad.add(xor.second)
                }
            }
        }

        val ors = rules.filter {
            it.first.contains("OR") && !it.first.contains("XOR")
        }

        val orInputs = ors.map { it.first }

        for (or in ors) {
            if (or.second.startsWith("z") && or.second != "z45") {
                bad.add(or.second)
            }
        }

        val ands = rules.filter {
            it.first.contains("AND")
        }

        for (and in ands) {
            if (and.second.startsWith("z")) {
                bad.add(and.second)
            }

            if (!and.first.matches("[xy]00 AND [xy]00".toRegex()) && !orInputs.any { it.contains(and.second) }) {
                bad.add(and.second)
            }
        }

        return bad.sorted().joinToString(",")
    }

    val testInput = readInput("day24_test")
    val testInput2 = readInput("day24_test2")

    check(part1(testInput) == 4L)
    check(part1(testInput2) == 2024L)

    val input = readInput("day24")
    println(part1(input))
    println(part2(input))
}
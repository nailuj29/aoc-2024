fun main() {
    fun hasConnection(a: String, b: String, map: List<Pair<String, String>>): Boolean {
        return map.any { a == it.first && b == it.second || b == it.first && a == it.second } || a == b
    }

    fun part1(input: List<String>): Int {
        val connections = mutableListOf<Pair<String, String>>()
        val toCheck = mutableSetOf<String>()
        for (line in input) {
            val computers = line.split("-")
            connections.add(computers[0] to computers[1])
            connections.add(computers[1] to computers[0])

            if (computers[0].startsWith("t")) {
                toCheck.add(computers[0])
            }

            if (computers[1].startsWith("t")) {
                toCheck.add(computers[1])
            }
        }

        val groups = mutableSetOf<Set<String>>()
        for (computer in toCheck) {
            val computerConnections = connections.filter { it.first == computer }.map { it.second }
            for (i in computerConnections.indices) {
                for (j in i+1..<computerConnections.size) {
                    if (hasConnection(computerConnections[i], computerConnections[j], connections)) {
                        groups.add(setOf(computerConnections[i], computerConnections[j], computer))
                    }
                }
            }
        }

        return groups.size
    }

    fun allConnections(a: String, map: List<Pair<String, String>>): Set<String> {
        val connections = mutableSetOf<String>()

        for (connection in map) {
            if (a == connection.first) {
                connections.add(connection.second)
            }
        }

        return connections
    }

    fun part2(input: List<String>): String {
        val connections = mutableListOf<Pair<String, String>>()
        val uniqueComputers = mutableSetOf<String>()
        for (line in input) {
            val computers = line.split("-")
            connections.add(computers[0] to computers[1])
            connections.add(computers[1] to computers[0])

            uniqueComputers.add(computers[0])
            uniqueComputers.add(computers[1])
        }

        val groups = mutableSetOf<Set<String>>()
        for (computer in uniqueComputers) {
            val connectedComputers = allConnections(computer, connections)

            val computers = connectedComputers.flatMap {
                allConnections(it, connections).filter { it in connectedComputers + computer }
            }.toSet()

            groups.add(computers)
        }

        val trueGroups = groups.filter {
            for (computer in it) {
                val others = it - computer

                for (otherComputer in others) {
                    if (others.any { c -> !hasConnection(otherComputer, c, connections) }) {
                        return@filter false
                    }
                }
            }

            return@filter true
        }.toSet()

        val best = trueGroups.maxByOrNull { it.size }!!

        return best.sorted().joinToString(",")
    }

    val testInput = readInput("day23_test")

    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")

    val input = readInput("day23")
    println(part1(input))
    println(part2(input))
}

fun main() {
    fun part1(input: List<String>): Int {
        val connections: List<Pair<String, String>> = input.map { it.split("-").zipWithNext().first() }.flatMap { listOf(it, Pair(it.second, it.first)) }

        fun isNodeVisitable(
            nextNode: String,
            nodes: List<String>
        ) : Boolean {
            return nextNode[0].isUpperCase() || nextNode[0].isLowerCase() && !nodes.contains(nextNode)
        }

        fun traverseGraph(nodes: List<String>): List<List<String>> {
            if (nodes.last() == "end") {
                return listOf(nodes)
            }
            return connections.filter { it.first == nodes.last() &&
                    isNodeVisitable(it.second, nodes) }
                .flatMap {
                    val newPath = mutableListOf<String>()
                    newPath.addAll(nodes)
                    newPath.add(it.second)
                    traverseGraph(newPath)
                }
        }

        val result = connections.filter { it.first == "start" }.flatMap {
            val possibleRoute = mutableListOf(it.first, it.second)
            traverseGraph(possibleRoute)
        }

        return result.size

    }

    fun part2(input: List<String>): Int {
        val connections: List<Pair<String, String>> = input.map { it.split("-").zipWithNext().first() }.flatMap { listOf(it, Pair(it.second, it.first)) }

        fun alreadyDupSmallNode(nodes: List<String>) : Boolean {
            return nodes.filter { it[0].isLowerCase() }.groupBy { it }.filter { it.value.size > 1 }.count() > 0
        }

        fun isNodeVisitable(
            nextNode: String,
            nodes: List<String>
        ) : Boolean {
            return if (nextNode[0].isUpperCase()) {
                true
            } else if (nextNode == "start") {
                false
            } else if (nextNode[0].isLowerCase() && !nodes.contains(nextNode)) {
                true
            } else nextNode[0].isLowerCase() && !alreadyDupSmallNode(nodes)
        }

        fun traverseGraph(nodes: List<String>): List<List<String>> {
            if (nodes.last() == "end") {
                return listOf(nodes)
            }
            return connections.filter { it.first == nodes.last() &&
                    isNodeVisitable(it.second, nodes) }
                .flatMap {
                    val newPath = mutableListOf<String>()
                    newPath.addAll(nodes)
                    newPath.add(it.second)
                    traverseGraph(newPath)
                }
        }

        val result = connections.filter { it.first == "start" }.flatMap {
            val possibleRoute = mutableListOf(it.first, it.second)
            traverseGraph(possibleRoute)
        }

        return result.size
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day12_test1")
    check(part2(testInput) == 36)
    testInput = readInput("Day12_test2")
    check(part2(testInput) == 103)
    testInput = readInput("Day12_test3")
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

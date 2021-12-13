
fun main() {
    fun part1(input: List<String>): Int {
        val connections: List<Pair<String, String>> = input.map { it.split("-").zipWithNext().first() }.flatMap { listOf(it, Pair(it.second, it.first)) }
        //val nodes = connections.flatMap { it.toList() }.distinct()

        fun traverseGraph(nodes: List<String>): List<List<String>> {
            if (nodes.last() == "end") {
                return listOf(nodes)
            }
            return connections.filter { it.first == nodes.last() &&
                    (it.second[0].isUpperCase() || it.second[0].isLowerCase() && !nodes.contains(it.second) ) }
                .flatMap {
                    val newPath = mutableListOf<String>()
                    newPath.addAll(nodes)
                    newPath.add(it.second)
                    traverseGraph(newPath)
                    //TODO what about end?
                }
        }

        val result = connections.filter { it.first == "start" }.flatMap {
            var possibleRoute = mutableListOf(it.first, it.second)
            traverseGraph(possibleRoute)
        }

        return result.size

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day12_test1")
    check(part1(testInput) == 10)
    testInput = readInput("Day12_test2")
    check(part1(testInput) == 19)
    testInput = readInput("Day12_test3")
    check(part1(testInput) == 226)

    val input = readInput("Day12")
    println(part1(input))
//    println(part2(input))
}

fun main() {

    data class Node (
        var key: Int? = null,
        var left: Node? = null,
        var right: Node? = null )

    data class ParseNodeResult (
        val remaining: String,
        val node: Node )

    fun parseTree(input: String): ParseNodeResult {
        val result = Node()
        val nextChar = input.first()
        if (nextChar == '[') {
            val leftResult = parseTree(input.drop(1))
            result.left = leftResult.node
            val rightResult = parseTree(leftResult.remaining.drop(1))
            result.right = rightResult.node
            return ParseNodeResult(rightResult.remaining.drop(1), result)
        } else if (nextChar.isDigit()) {
//            var charsToDrop = 1
//            if (input[1] == ',')
//                charsToDrop = 2
            return ParseNodeResult(input.drop(1), Node(nextChar.digitToInt()))
        } else {
            TODO()
        }
    }

    fun part1(input: List<String>): Int {
        //TODO multiple lines
        val Node = parseTree(input[0])
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 0)

    val input = readInput("Day18")
//    println(part1(input))
//    println(part2(input))
}

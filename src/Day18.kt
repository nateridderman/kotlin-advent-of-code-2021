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
            return ParseNodeResult(input.drop(1), Node(nextChar.digitToInt()))
        } else {
            TODO()
        }
    }

    fun part1(input: List<String>): Int {
        input.forEach {
            val Node = parseTree(it)
        }

        //need a method to traverse the tree left to right, stopping if an action has been performed
        //need a method to split a number
        //need a method to explode a number (finding the next numbers to the left and right, if any)

        //If any pair is nested inside four pairs, the leftmost such pair explodes.
        //If any regular number is 10 or greater, the leftmost such regular number splits.

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

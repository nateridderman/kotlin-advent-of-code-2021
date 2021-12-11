import java.util.*

enum class PairResult {
    PAIR, NEW, INVALID
}

fun main() {

    val openOperators = listOf('[', '(', '{' ,'<')
    val pairs = listOf(Pair('[', ']'), Pair('(', ')'), Pair('{', '}'), Pair('<', '>'))

    fun checkPair(last: Char?, next: Char) : PairResult {
        return if (last != null && pairs.contains(Pair(last, next))) {
            PairResult.PAIR
        } else if (openOperators.contains(next)){
            PairResult.NEW
        } else {
            return PairResult.INVALID
        }
    }

    fun parseNext(stack: Stack<Char>, remaining: String): Char {
        return when (checkPair(if (stack.empty()) null else stack.peek(), remaining[0])) {
            PairResult.PAIR -> {
                stack.pop()
                if (remaining.length > 1) {
                    parseNext(stack, remaining.substring(1))
                } else if (stack.size == 0){
                    '#'
                } else {
                    'I'
                }
            }
            PairResult.NEW -> {
                stack.push(remaining[0])
                if (remaining.length > 1) {
                    parseNext(stack, remaining.substring(1))
                } else {
                    'I'
                }
            }
            PairResult.INVALID -> {
                remaining[0]
            }
        }
    }

    fun parseNext2(stack: Stack<Char>, remaining: String): Stack<Char>? {
        return when (checkPair(if (stack.empty()) null else stack.peek(), remaining[0])) {
            PairResult.PAIR -> {
                stack.pop()
                if (remaining.length > 1) {
                    parseNext2(stack, remaining.substring(1))
                } else {
                    stack
                }
            }
            PairResult.NEW -> {
                stack.push(remaining[0])
                if (remaining.length > 1) {
                    parseNext2(stack, remaining.substring(1))
                } else {
                    stack
                }
            }
            PairResult.INVALID -> {
                stack
            }
        }
    }

    fun parsePart1(line: String): Char {
        val operatorStack = Stack<Char>()
        operatorStack.push(line.first())
        return parseNext(operatorStack, line.substring(1))
    }

    fun parsePart2(line: String): List<Char> {
        val operatorStack = Stack<Char>()
        operatorStack.push(line.first())
        val leftOverChars = ArrayList(parseNext2(operatorStack, line.substring(1))!!).toList()
        //println(leftOverChars)
        return leftOverChars
    }

    fun part1(input: List<String>): Int {
        return input.map { parsePart1(it) }
            .map {
                if (it == '#' || it == 'I') {
                    0
                } else if (it == ')') {
                    3
                } else if (it == ']') {
                    57
                } else if (it == '}') {
                    1197
                } else if (it == '>') {
                    25137
                } else {
                    -1
                }
            }.sum()
    }

//    ): 1 point.
//    ]: 2 points.
//    }: 3 points.
//    >: 4 points.
    fun charToScorePart2(input: Char) : Int {
        return when (input) {
            '(' -> 1
            '[' -> 2
            '{' -> 3
            '<' -> 4
            else -> 0
        }
    }

    fun part2(input: List<String>): Long {
        val listOfLineScores = input.map { parsePart1(it) }
            .mapIndexedNotNull { index, it ->
                if (it == 'I') {
                    parsePart2(input[index]).reversed()
                        .fold(0L) {
                            acc, c -> acc * 5 + charToScorePart2(c)
                    }
                } else {
                    null
                }
            }.sorted()
        return listOfLineScores[listOfLineScores.size/2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

data class Board(var inputLines: List<String>) {
    val patternsToCheck: MutableList<List<Int>> = mutableListOf()
    init {
        val elements = inputLines.map {
            it.trim(' ').replace("  ", " ").split(" ").map { it.toInt() }
        }
        patternsToCheck.addAll(elements)
        for (i in 0 .. 4) {
            patternsToCheck.add((0 .. 4).map { elements[it][i] })
        }
    }
}

fun main() {

    fun solvePartOne(numbersDrawn : List<Int>, boards: List<Board>): Int {
        var i = 5
        var result: Board? = null
        while (result == null) {
            val testNumbers = numbersDrawn.take(i++)
            result = boards.firstOrNull {
                it.patternsToCheck.any { testNumbers.containsAll(it) }
            }
        }
        --i
        val sumOfNotMatching = result.patternsToCheck.take(5).flatten()
            .filter { !numbersDrawn.take(i).contains(it) }
            .sum()
        return sumOfNotMatching * numbersDrawn[i-1]
    }

    fun part1(input: List<String>): Int {
        val numbersDrawn = input[0].split(',').map { it.toInt() }

        val boards = mutableListOf<Board>()
        var remainingInput = input.drop(2)
        while (remainingInput.size > 0) {
            boards.add(Board(remainingInput.take(5)))
            remainingInput = remainingInput.drop(6)
        }
        return solvePartOne(numbersDrawn, boards)
    }

    fun solvePartTwo(numbersDrawn : List<Int>, boards: List<Board>): Int {
        var i = 5
        var boardsLeft = boards.toMutableList()
        var lastBoardFound: Board? = null
        while (boardsLeft.size > 0) {
            val testNumbers = numbersDrawn.take(i++)
            boardsLeft.removeIf {
                it.patternsToCheck.any { testNumbers.containsAll(it) }
            }
            if (boardsLeft.size == 1) {
                lastBoardFound = boardsLeft[0]
            }
        }
        --i
        val sumOfNotMatching = lastBoardFound!!.patternsToCheck.take(5).flatten()
            .filter { !numbersDrawn.take(i).contains(it) }
            .sum()
        return sumOfNotMatching * numbersDrawn[i-1]
    }

    fun part2(input: List<String>): Int {
        val numbersDrawn = input[0].split(',').map { it.toInt() }

        val boards = mutableListOf<Board>()
        var remainingInput = input.drop(2)
        while (remainingInput.size > 0) {
            boards.add(Board(remainingInput.take(5)))
            remainingInput = remainingInput.drop(6)
        }
        return solvePartTwo(numbersDrawn, boards)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

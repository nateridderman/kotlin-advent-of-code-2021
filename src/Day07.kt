import java.lang.Math.abs

fun main() {

    fun partOneFuelUsed(x: Int, crabPositions: List<Int>): Int {
        return crabPositions.sumOf { abs(it - x) }
    }

    fun part1(input: List<String>): Int {
        var optimalDistance: Int? = null
        val crabPositions = input[0].split(",").map { it.toInt() }.toMutableList()
        crabPositions.sort()

        (crabPositions.first() .. crabPositions.last()).takeWhile {
            val newDistance = partOneFuelUsed(it, crabPositions)
            if (optimalDistance == null || newDistance < optimalDistance!!) {
                optimalDistance = newDistance
                true
            } else {
                false
            }
        }

        return optimalDistance!!
    }

    fun partTwoFuelUsed(x: Int, crabPositions: List<Int>): Int {
        return crabPositions.sumOf {
            val distance = abs(it - x)
            (distance) * (distance + 1)/2
        }
    }

    fun part2(input: List<String>): Int {
        val crabPositions = input[0].split(",").map { it.toInt() }.toMutableList()
        crabPositions.sort()

        var optimalDistance: Int? = null

        (crabPositions.first() .. crabPositions.last()).takeWhile {
            val newDistance = partTwoFuelUsed(it, crabPositions)
            if (optimalDistance == null || newDistance < optimalDistance!!) {
                optimalDistance = newDistance
                true
            } else {
                false
            }
        }

        return optimalDistance!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

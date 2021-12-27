import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    fun part1(input: List<String>): Int {
        val window = input[0].substringAfter(": ")
        val xWindow = window.split(", ")[0].substringAfter("x=").split("..")
        val yWindow = window.split(", ")[1].substringAfter("y=").split("..")
        val xRangePair = Pair(xWindow[0].toInt(), xWindow[1].toInt())
        val yRangePair = Pair(yWindow[0].toInt(), yWindow[1].toInt())

        val yRange = yWindow[0].toInt() .. yWindow[1].toInt()

        //1/2(n)(n+1)
        val smallestX = (1 .. xRangePair.first).filter { it * (it + 1 ) / 2 >= xRangePair.first  }.first()
        val biggestX = (xRangePair.second downTo 1).filter { it * (it + 1) / 2 <= xRangePair.second }.first()

        val minSteps = smallestX
        //could find a smaller max but won't hurt to calculate more
        val maxSteps = biggestX + yRangePair.second - yRangePair.first + 30

        //y(n) = y * n - 1/2(n)(n+1)
        //val yAtMinSteps = n * (y - (n+1)/2)

        val maxY = (minSteps .. maxSteps).flatMap { n->
            val yVelocityForTopYWindow = ceil((yRangePair.first + (n * (n+1))/2)/n.toDouble()).toInt()
            val yVelocityForBottomWindow = floor((yRangePair.second + (n * (n+1))/2)/n.toDouble()).toInt()
            (yVelocityForBottomWindow .. yVelocityForTopYWindow).map { Pair(n, it) }
        }.filter {
            val yatn = it.second * it.first - (it.first * (it.first +1))/2
            val xatn = it.first * (it.first + 1)/2
            return@filter (yatn >= yRangePair.first && yatn <= yRangePair.second && xatn >= xRangePair.first && xatn <= xRangePair.second)
        }.maxByOrNull {
            it.second
        }

        val yVals = (1..maxY!!.first).map {
            maxY.second * it - (it * (it +1))/2
        }
        return yVals.maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 45)

//    val input = readInput("Day17")
//    println(part1(input))
//    println(part2(input))
}

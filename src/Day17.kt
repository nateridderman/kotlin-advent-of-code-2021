import kotlin.math.floor

fun main() {

    data class Result(val xVelocity: Int, val yVelocity: Int, val n: Int)

    fun part1(input: List<String>): Int {
        val window = input[0].substringAfter(": ")
        val xWindow = window.split(", ")[0].substringAfter("x=").split("..")
        val yWindow = window.split(", ")[1].substringAfter("y=").split("..")
        val xRangePair = Pair(xWindow[0].toInt(), xWindow[1].toInt())
        val yRangePair = Pair(yWindow[0].toInt(), yWindow[1].toInt())

        val xRange = xWindow[0].toInt() .. xWindow[1].toInt()
        val yRange = yWindow[0].toInt() .. yWindow[1].toInt()

        //sum of natural numbers up to n is 1/2(n)(n+1)
        val smallestX = (1 .. xRangePair.first).filter { it * (it + 1 ) / 2 >= xRangePair.first  }.first()
        val biggestX = xRangePair.second

        //y(n) = y * n - 1/2(n)(n+1)
        //val yAtMinSteps = n * (y - (n+1)/2)

        val validPositions = mutableListOf<Result>()

        (smallestX .. biggestX).forEach { x ->
            val positionsAtX = x .. x + 20000
            val yVelocityForBottomWindow = floor((yRangePair.second + (x * (x+1))/2)/x.toDouble()).toInt()
            val yVelocitiesToScan = yVelocityForBottomWindow .. yVelocityForBottomWindow + 300
            positionsAtX.forEach{ n ->
                yVelocitiesToScan.forEach { y ->
                    val yatn = y * n - (n * (n +1))/2
                    val xatn = x * (x + 1)/2
                    if (yRange.contains(yatn) && xRange.contains(xatn)) {
                        validPositions.add(Result(x,y,n))
                    }
                }
            }
        }

        val maxY = validPositions.maxByOrNull { it.yVelocity }

        val yVals = (1..maxY!!.n).map {
            maxY.yVelocity * it - (it * (it +1))/2
        }
        return yVals.maxOf { it }
    }

    fun part2(input: List<String>): Int {
        val window = input[0].substringAfter(": ")
        val xWindow = window.split(", ")[0].substringAfter("x=").split("..")
        val yWindow = window.split(", ")[1].substringAfter("y=").split("..")
        val xRangePair = Pair(xWindow[0].toInt(), xWindow[1].toInt())
        val yRangePair = Pair(yWindow[0].toInt(), yWindow[1].toInt())

        val xRange = xWindow[0].toInt() .. xWindow[1].toInt()
        val yRange = yWindow[0].toInt() .. yWindow[1].toInt()

        //sum of natural numbers up to n is 1/2(n)(n+1)
        val smallestX = (1 .. xRangePair.first).filter { it * (it + 1 ) / 2 >= xRangePair.first  }.first()
//        val smallestX = 7
        val biggestX = xRangePair.second

        //y(n) = y * n - 1/2(n)(n+1)
        //val yAtMinSteps = n * (y - (n+1)/2)

        val validPositions = mutableListOf<Result>()

        (smallestX .. biggestX).forEach { x ->
            val nRange = 1 .. x + 20000
            val yVelocityForBottomWindow = yRangePair.first
            val yVelocitiesToScan = yVelocityForBottomWindow .. yVelocityForBottomWindow + 600
            yVelocitiesToScan.forEach { y ->
                nRange.takeWhile { n ->
                    val yatn = y * n - ((n-1) * (n))/2
                    val xatn = if (n >= x) {
                        x * (x + 1)/2
                    } else {
                        (x downTo x-(n-1)).sum()
                    }
                    if (yRange.contains(yatn) && xRange.contains(xatn)) {
                        validPositions.add(Result(x,y,n))
                    } else if (yatn < yRange.start) {
                        return@takeWhile false
                    }
                    true
                }
            }
        }

        val result = validPositions.groupBy { Pair(it.xVelocity, it.yVelocity) }
        return result.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
//    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}

import java.lang.Integer.min
import java.lang.Math.max

fun main() {

    fun rangeBetween(a: Int, b: Int) = min(a, b) .. max(a, b)

    fun part1(input: List<String>): Int {
        val pointArray = hashMapOf<Int, HashMap<Int, Int>>()

        fun populatePointArray(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>) {
            if (firstPoint.first == secondPoint.first) {
                //vertical line
                (rangeBetween(firstPoint.second, secondPoint.second)).map {
                    val newValue = pointArray.getOrPut(it){hashMapOf()}.getOrPut(firstPoint.first){0}
                    pointArray.get(it)!!.put(firstPoint.first, newValue + 1)
                }
            } else if (firstPoint.second == secondPoint.second) {
                //horizontal line
                val lineMap = pointArray.getOrPut(firstPoint.second){hashMapOf()}
                (rangeBetween(firstPoint.first, secondPoint.first)).map {
                    val newValue = lineMap.getOrPut(it){0}
                    lineMap.put(it, newValue + 1)
                }
            }
        }

        input.map {
            it.split(" -> ")
        }.map {
            val firstPoint: Pair<Int, Int> = Pair(it[0].substringBefore(',').toInt(), it[0].substringAfter(',').toInt())
            val secondPoint: Pair<Int, Int> = Pair(it[1].substringBefore(',').toInt(), it[1].substringAfter(',').toInt())
            populatePointArray(firstPoint, secondPoint)
        }

        return pointArray.entries.map {
            it.value.entries.filter { it.value > 1 }.count()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val pointArray = hashMapOf<Int, HashMap<Int, Int>>()

        fun populatePointArray(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>) {
            if (firstPoint.first == secondPoint.first) {
                //vertical line
                (rangeBetween(firstPoint.second, secondPoint.second)).map {
                    val newValue = pointArray.getOrPut(it){hashMapOf()}.getOrPut(firstPoint.first){0}
                    pointArray.get(it)!!.put(firstPoint.first, newValue + 1)
                }
            } else if (firstPoint.second == secondPoint.second) {
                //horizontal line
                val lineMap = pointArray.getOrPut(firstPoint.second){hashMapOf()}
                (rangeBetween(firstPoint.first, secondPoint.first)).map {
                    val newValue = lineMap.getOrPut(it){0}
                    lineMap.put(it, newValue + 1)
                }
            } else if (kotlin.math.abs(firstPoint.first - secondPoint.first) == kotlin.math.abs(firstPoint.second - secondPoint.second)
            ) {
                //diagonal line
                //increasing slope
                if ((firstPoint.first - secondPoint.first) * (firstPoint.second - secondPoint.second) > 0) {
                    val smallerX = min(firstPoint.first, secondPoint.first)
                    val smallerY = min(firstPoint.second, secondPoint.second)
                    (rangeBetween(firstPoint.second, secondPoint.second)).map {
                        val newValue = pointArray.getOrPut(it){hashMapOf()}.getOrPut(smallerX + it - smallerY){0}
                        pointArray.get(it)!!.put(smallerX + it - smallerY, newValue + 1)
                    }
                } else {
                    val largerX = max(firstPoint.first, secondPoint.first)
                    val smallerY = min(firstPoint.second, secondPoint.second)
                    (rangeBetween(firstPoint.second, secondPoint.second)).map {
                        val newValue = pointArray.getOrPut(it){hashMapOf()}.getOrPut(largerX - it + smallerY){0}
                        pointArray.get(it)!!.put(largerX - it + smallerY, newValue + 1)
                    }
                }
            }
        }

        input.map {
            it.split(" -> ")
        }.map {
            val firstPoint: Pair<Int, Int> = Pair(it[0].substringBefore(',').toInt(), it[0].substringAfter(',').toInt())
            val secondPoint: Pair<Int, Int> = Pair(it[1].substringBefore(',').toInt(), it[1].substringAfter(',').toInt())
            populatePointArray(firstPoint, secondPoint)
        }

        return pointArray.entries.map {
            it.value.entries.filter { it.value > 1 }.count()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    println(part2(testInput))

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

import java.lang.Math.abs

fun main() {

    fun fold(foldInstructions: String, dots: List<Point>): List<Point> {
        return dots.map {
            val instructions = foldInstructions.split(" along ")[1].split("=")
            val foldDistance = instructions[1].toInt()
            if (instructions[0] == "y") {
                if (it.y > foldDistance) {
                    Point(it.x,foldDistance - abs(foldDistance-it.y))
                } else {
                    Point(it.x, it.y)
                }
            } else {
                if (it.x > foldDistance) {
                    Point(foldDistance - abs(foldDistance-it.x), it.y)
                } else {
                    Point(it.x, it.y)
                }
            }
        }.distinct()
    }

    fun part1(input: List<String>): Int {
        val dots = input.takeWhile { it.isNotBlank() }.map {
            Point(it.substringBefore(',').toInt(), it.substringAfter(',').toInt())
        }

        val foldInstructions = input.first { it.startsWith("fold") }

        //visible dots after first fold
        return fold(foldInstructions, dots).size
    }

    fun part2(input: List<String>): Int {
        var dots = input.takeWhile { it.isNotBlank() }.map {
            Point(it.substringBefore(',').toInt(), it.substringAfter(',').toInt())
        }

        val foldInstructions = input.filter { it.startsWith("fold") }

        foldInstructions.forEach {
            dots = fold(it, dots)
        }
        val xMax = dots.maxOf { it.x }
        val yMax = dots.maxOf { it.y }
        repeat (yMax+1) {
            val stringToPrint = (0..xMax).mapIndexed { index, i ->
                if (dots.contains(Point(index,it))) {
                    "*"
                } else {
                    " "
                }
            }.joinToString("")
            println(stringToPrint)
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

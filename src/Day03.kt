import java.lang.RuntimeException

fun main() {
    fun part1(input: List<String>): Int {
        var forwardProgress = 0
        var depth = 0
        input.map { it.split(" ") }
            .map {
                when (it[0]) {
                    "forward" -> forwardProgress += it[1].toInt()
                    "down" -> depth += it[1].toInt()
                    "up" -> depth -= it[1].toInt()
                    else -> RuntimeException()
                }
            }
        return forwardProgress * depth
    }

    fun part2(input: List<String>): Int {
//        var forwardProgress = 0
//        var aim = 0
//        var depth = 0
//        input.map { it.split(" ") }
//            .map {
//                when (it[0]) {
//                    "forward" -> {
//                        forwardProgress += it[1].toInt()
//                        depth += aim * it[1].toInt()
//                    }
//                    "down" -> aim += it[1].toInt()
//                    "up" -> aim -= it[1].toInt()
//                    else -> RuntimeException()
//                }
//            }
//        return forwardProgress * depth
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
//    println(part1(input))
//    println(part2(input))
}

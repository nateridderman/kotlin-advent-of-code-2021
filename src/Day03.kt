fun main() {
    fun part1(input: List<String>): Int {
        val sumPerPosition: MutableList<Int> = MutableList(input[0].length) {
            0
        }

        val gammaRateAsString = (0 until input.first().length).joinToString("") { position ->
            input.count { it[position] == '1' }.let { if (it > input.size/2.0f) "1" else "0" }
        }

        val gammaRate = Integer.parseInt(gammaRateAsString, 2)

        val epsilonRateAsString = gammaRateAsString.map {
            if (it == '0') {
                '1'
            } else {
                '0'
            }
        }.joinToString(separator = "")
        val epsilonRate = Integer.parseInt(epsilonRateAsString, 2)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))
//    println(part2(input))
}

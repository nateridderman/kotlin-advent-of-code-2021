fun main() {
    fun part1(input: List<String>): Int {
        val reportsSize = input.size
        val numOfPositions = input[0].length
        var sumPerPosition: MutableList<Int> = MutableList(numOfPositions) {
            0
        }
        input.forEach { s1 ->
            s1.toCharArray().forEachIndexed { index, s ->
                run {
                    if (s.code == 49) {
                        sumPerPosition[index]++
                    }
                }
            }
        }

        val gammaRateAsString = sumPerPosition.joinToString(separator = "") {
            if (it > reportsSize/2.0f) {
                "1"
            } else {
                "0"
            }
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
//        println("$epsilonRate and ${epsilonRate.times(gammaRate.toUInt()).toInt()}")
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

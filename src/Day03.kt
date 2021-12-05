fun main() {
    fun part1(input: List<String>): Int {
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
        var oxygenGenerator = input.toMutableList()
        var index = 0
        while (oxygenGenerator.size > 1) {
            val numberOfOnes = oxygenGenerator.count { it[index] == '1' }
            if (numberOfOnes >= oxygenGenerator.size - numberOfOnes) {
                oxygenGenerator = oxygenGenerator.filter { it[index] == '1' }.toMutableList()
            } else {
                oxygenGenerator = oxygenGenerator.filter { it[index] == '0' }.toMutableList()
            }
            index++
        }

        var co2Generator = input.toMutableList()
        index = 0
        while (co2Generator.size > 1) {
            val numberOfOnes = co2Generator.count { it[index] == '1' }
            if (numberOfOnes < co2Generator.size - numberOfOnes) {
                co2Generator = co2Generator.filter { it[index] == '1' }.toMutableList()
            } else {
                co2Generator = co2Generator.filter { it[index] == '0' }.toMutableList()
            }
            index++
        }

        return Integer.parseInt(oxygenGenerator[0], 2) * Integer.parseInt(co2Generator[0], 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

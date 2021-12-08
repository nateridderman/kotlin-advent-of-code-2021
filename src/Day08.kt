fun main() {
    fun part1(input: List<String>): Int {
        return input.flatMap { it.substringAfter("| ").split(" ") }
            .filter { it.length <= 4 || it.length == 7 }
            .count()
    }

    fun solveDigits(input: MutableList<List<Char>>) : Map<String, String> {
        val dictionary = mutableMapOf<String, String>()
        val stringFor8 = input.first { it.size == 7}
        input.remove(stringFor8)
        dictionary[stringFor8.joinToString("")] = "8"
        val stringFor7 = input.first { it.size == 3}
        input.remove(stringFor7)
        dictionary[stringFor7.joinToString("")] = "7"
        val stringFor4 = input.first { it.size == 4}
        input.remove(stringFor4)
        dictionary[stringFor4.joinToString("")] = "4"
        val stringFor1 = input.first { it.size == 2}
        input.remove(stringFor1)
        dictionary[stringFor1.joinToString("")] = "1"
        val stringFor3 = input.first { it.size == 5 && it.containsAll(stringFor7) }
        input.remove(stringFor3)
        dictionary[stringFor3.joinToString("")] = "3"
        val stringFor9 = input.first { it.size == 6 && it.containsAll(stringFor3) }
        input.remove(stringFor9)
        dictionary[stringFor9.joinToString("")] = "9"
        val stringFor5 = input.first { it.size == 5 && stringFor9.containsAll(it) }
        input.remove(stringFor5)
        dictionary[stringFor5.joinToString("")] = "5"
        val stringFor2 = input.first { it.size == 5}
        input.remove(stringFor2)
        dictionary[stringFor2.joinToString("")] = "2"
        val stringFor6 = input.first { it.size == 6 && it.containsAll(stringFor5)}
        input.remove(stringFor6)
        dictionary[stringFor6.joinToString("")] = "6"
        //shortcut for the last
        dictionary[input[0].joinToString("")] = "0"
        return dictionary
    }

    fun part2(input: List<String>): Int {
        val encodedOutputDigits = input.map { it.substringAfter("| ").split(" ").map { it.toCharArray().sorted().joinToString("") } }
        return input.map { it.substringBefore("| ").trim().split(" ").map { it.toCharArray().sorted() } }.zip(encodedOutputDigits)
            .sumOf {
                val dictionary : Map<String, String> = solveDigits(it.first.toMutableList())
                val decodedOutputDigits = it.second.map { it2 -> dictionary[it2] }.joinToString("")
                decodedOutputDigits.toInt()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

import java.math.BigDecimal
import java.math.RoundingMode

fun main() {

    fun part1(input: List<String>): Int {
        val tokenInputs = input.map { it.toCharArray().map { it.toString() } }
        val finalTokens = tokenInputs.reduce { acc, it ->
            var reducedSomething = true
            var tokens = listOf("[").plus(acc).plus(listOf(",")).plus(it).plus(listOf("]")).toMutableList()
            while (reducedSomething) {
                reducedSomething = false
                var depth = 0
                tokens.forEachIndexed { i, token ->
                    if (!reducedSomething) {
                        if (token == "[") {
                            depth++
                        } else if (token == "]") {
                            depth--
                        } else if (token[0].isDigit() && depth >= 5 && tokens[i+1] == "," && tokens[i+2][0].isDigit() ) {
                            val indexOfPrev = tokens.subList(0, i).reversed().indexOfFirst { it[0].isDigit() }
                            val indexOfNext = tokens.subList(i+3, tokens.size).indexOfFirst { it[0].isDigit() }

                            val debugList = tokens
                            val first = token.toInt()
                            val second = tokens[i+2].toInt()

                            tokens = tokens.take(i-1)
                                .plus(listOf("0"))
                                .plus(tokens.subList(i+4, tokens.size)).toMutableList()

                            val frozenList = tokens

                            if (indexOfPrev != -1) {
                                val newPrev = tokens[i - indexOfPrev - 1].toInt() + first
                                tokens = tokens.take(i - indexOfPrev - 1).plus(listOf(newPrev.toString())).plus(tokens.subList(i - indexOfPrev, tokens.size )).toMutableList()
                            }
                            if (indexOfNext != -1) {
                                val newNext = frozenList[i + indexOfNext - 1].toInt() + second
                                tokens = tokens.take(i + indexOfNext - 1).plus(mutableListOf(newNext.toString())).plus(tokens.subList(i + indexOfNext, tokens.size)).toMutableList()
                            }
                            reducedSomething = true
                            //println(tokens.joinToString(""))
                        }
                    }
                }
                depth = 0
                tokens.forEachIndexed { i, token ->
                    if (!reducedSomething) {
                        if (token == "[") {
                            depth++
                        } else if (token == "]") {
                            depth--
                        } else if (token[0].isDigit() && token.toInt() >= 10) {
                            val biggie = BigDecimal(token)
                            val a = biggie.divide(BigDecimal(2), RoundingMode.FLOOR).toInt()
                            val b = biggie.divide(BigDecimal(2), RoundingMode.CEILING).toInt()

                            tokens = tokens.take(i)
                                .plus(listOf("[", a.toString(), ",", b.toString(), "]"))
                                .plus(tokens.subList(i+1, tokens.size))
                                .toMutableList()
                            reducedSomething = true
                            //println(tokens.joinToString(""))
                        }
                    }
                }
            }
            //println(tokens.joinToString(""))
            tokens
        }

        var tokenReduction = finalTokens.toMutableList()
        var reducedSomething = true
        while (reducedSomething) {
            reducedSomething = false
            val toReplace = tokenReduction.withIndex().windowed(3)
                .firstOrNull {
                    //println(it.toString())
                    it[0].value[0].isDigit() && it[1].value[0] == ',' && it[2].value[0].isDigit()
                }
            toReplace?.let {
                reducedSomething = true
                val replaceWith = 3 * it[0].value.toInt() + 2 * it[2].value.toInt()
                tokenReduction = tokenReduction.take(it[0].index-1).plus(replaceWith.toString()).plus(tokenReduction.subList(it[2].index+2, tokenReduction.size)).toMutableList()
            }
        }

        var finalResultString = finalTokens.joinToString("")
        println(finalResultString)
        return tokenReduction[0].toInt()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day18_test2")
    check(part1(testInput) == 3488)
    testInput = readInput("Day18_test5")
    check(part1(testInput) == 4140)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}


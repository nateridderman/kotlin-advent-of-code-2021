import java.math.BigDecimal
import java.math.RoundingMode

fun main() {

    fun part1(input: List<String>): String {
        val result = input.reduce { acc, it ->
            println("\nfor $it")
            var reducedSomething = true
            var newList = "[$acc,$it]"
            var tokens = mutableListOf<String>()
            while (reducedSomething) {
                reducedSomething = false
                var depth = 0
                tokens.clear()
                val iter = newList.toCharArray().toList().listIterator()
                while (iter.hasNext()) {
                    val nextChar = iter.next()
                    if (tokens.isNotEmpty() && tokens.last()[0].isDigit() && nextChar.isDigit()) {
                        tokens[tokens.size-1] = tokens.last()[0].plus(nextChar.toString())
                    } else {
                        tokens.add(nextChar.toString())
                    }
                }
                tokens.forEachIndexed { i, token ->
                    if (!reducedSomething) {
                        if (token == "[") {
                            depth++
                        } else if (token == "]") {
                            depth--
                        } else if (token[0].isDigit()) {
                            if (depth >= 5 && tokens[i+1] == "," && tokens[i+2][0].isDigit() ) {
                                val indexOfPrev = tokens.subList(0, i).reversed().indexOfFirst { it[0].isDigit() }
                                val indexOfNext = tokens.subList(i+3, tokens.size).indexOfFirst { it[0].isDigit() }

                                val debugList = newList
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
                                println(newList)
                            } else if (token.toInt() >= 10) {
                                val biggie = BigDecimal(token)
                                val a = biggie.divide(BigDecimal(2), RoundingMode.FLOOR).toInt()
                                val b = biggie.divide(BigDecimal(2), RoundingMode.CEILING).toInt()

                                tokens = tokens.take(i)
                                    .plus(listOf("[", a.toString(), ",", b.toString(), "]"))
                                    .plus(tokens.subList(i+1, tokens.size))
                                    .toMutableList()
                                reducedSomething = true
                                println(newList)
                            }
                        } else {
                            check(token == ",")
                            //it's a comma
                        }
                    }
                }
                if (reducedSomething) {
                    newList = tokens.joinToString("")
                }
            }
            println("newList after reduction: $newList")
            newList
        }

        println(result)
        //If any regular number is 10 or greater, the leftmost such regular number splits.
        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test3")
    check(part1(testInput) == "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]")

    val input = readInput("Day18")
//    println(part1(input))
//    println(part2(input))
}

//[[3,[2,[8,0]]],[9,[5,[7,0]]]]

//[[[[0,7],4],[[7,8],[6,0]]],[8,1]]

//[[[[4,0],[5,4]],[[7,7],[6,0]]],[[[6,6],[0,5]],[[5,[6,6]],[0,[7,[7,8]]]]]]


//[[[[4,0],[5,4]],[[7,7],[6,0]]],[[[6,6],[5,5]],[[0,6],[[6,7],0]]]]
//turned into
//[[[[4,0],[5,4]],[[7,7],[6,0]]],[[[6,6],[5,5]],[[0,[6,6]],[0,7]]]]
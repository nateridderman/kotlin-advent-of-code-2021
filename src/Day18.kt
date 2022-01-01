import java.math.BigDecimal
import java.math.RoundingMode

fun main() {

    fun part1(input: List<String>): String {
        val result = input.reduce { acc, it ->
            println("\nfor $it")
            var reducedSomething = true
            var newList = "[$acc,$it]"
            while (reducedSomething) {
                reducedSomething = false
                var depth = 0
                newList.toCharArray().forEachIndexed { i, token ->
                    if (!reducedSomething) {
                        if (token == '[') {
                            depth++
                        } else if (token == ']') {
                            depth--
                        } else if (token.isDigit()) {
                            if (depth >= 5 && newList[i+1] == ',' && newList[i+2].isDigit() ) {

                                val indexOfPrev = newList.substring(0, i).reversed().indexOfFirst { it.isDigit() }
                                val indexOfNext = newList.substring(i+3).indexOfFirst { it.isDigit() }

                                val debugList = newList
                                val first = token.digitToInt()
                                val second = newList[i+2].digitToInt()

                                newList = newList.take(i-1).plus('0').plus(
                                    newList.substring(i+4)
                                )

                                val frozenList = newList

                                var extraSwap = 0
                                if (indexOfPrev != -1) {
                                    val newPrev = newList[i - indexOfPrev - 1].digitToInt() + first
                                    val result = if (newPrev >= 10) {
                                        extraSwap = 4
                                        val biggie = BigDecimal(newPrev)
                                        "[" + biggie.divide(BigDecimal(2), RoundingMode.FLOOR).toInt()  + "," + biggie.divide(BigDecimal(2), RoundingMode.CEILING).toInt() + "]"
                                    } else {
                                        newPrev.toString()
                                    }
                                    newList = newList.take(i - indexOfPrev - 1) + result + newList.substring(i - indexOfPrev )
                                }
                                if (indexOfNext != -1) {
                                    val newNext = frozenList[i + indexOfNext - 1].digitToInt() + second
                                    val result = if (newNext >= 10) {
                                        val biggie = BigDecimal(newNext)
                                        "[" + biggie.divide(BigDecimal(2), RoundingMode.FLOOR).toInt()  + "," + biggie.divide(BigDecimal(2), RoundingMode.CEILING).toInt() + "]"
                                    } else {
                                        newNext.toString()
                                    }
                                    newList = newList.take(i + indexOfNext + extraSwap - 1) + result + newList.substring(i + indexOfNext + extraSwap)
                                }
                                reducedSomething = true
                                println(newList)
                            }
                        } else {
                            check(token == ',')
                            //it's a comma
                        }
                    }
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
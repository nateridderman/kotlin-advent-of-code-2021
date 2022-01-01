
fun main() {

    fun part1(input: List<String>): Int {
        input.forEach {
            println("\nfor $it")
            var reducedSomething = true
            var newList: String = it
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

                                val first = token.digitToInt()
                                val second = newList[i+2].digitToInt()

                                newList = newList.take(i-1).plus('0').plus(
                                    newList.substring(i+4)
                                )
                                var extraSwap = 0
                                if (indexOfPrev != -1) {
                                    val newPrev = newList[i - indexOfPrev - 1].digitToInt() + first
                                    val result = if (newPrev > 10) {
                                        extraSwap = 4
                                        "[" + Math.floor(newPrev/2.0).toInt()  + "," + Math.ceil(newPrev/2.0).toInt() + "]"
                                    } else {
                                        newPrev.toString()
                                    }
                                    newList = newList.take(i - indexOfPrev - 1) + result + newList.substring(i - indexOfPrev )
                                }
                                if (indexOfNext != -1) {
                                    //even this doesn't work because the index is wrong. can i fix by checking the size deltas btw the two?
                                    val newPrev = newList[i + indexOfNext +extraSwap - 1].digitToInt() + second
                                    val result = if (newPrev > 10) {
                                        "[" + Math.floor(newPrev/2.0).toInt()  + "," + Math.ceil(newPrev/2.0).toInt() + "]"
                                    } else {
                                        newPrev.toString()
                                    }
                                    newList = newList.take(i + indexOfNext + extraSwap - 1) + result + newList.substring(i + indexOfNext + extraSwap)
                                    //newList = StringBuilder(newList).also { it.setCharAt(i + indexOfNext - 1, newList[i + indexOfNext -1].plus(second)) }.toString()
                                }
                                reducedSomething = true
                            }
                        } else {
                            //it's a comma
                        }
                    }
                }
            }
            println("newList after reduction: $newList")

        }

        //If any regular number is 10 or greater, the leftmost such regular number splits.
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 0)

    val input = readInput("Day18")
//    println(part1(input))
//    println(part2(input))
}

//[[3,[2,[8,0]]],[9,[5,[7,0]]]]

//[[[[0,7],4],[[7,8],[6,0]]],[8,1]]

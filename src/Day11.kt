
fun main() {

    fun part1(input: List<String>): Int {
        //get input into matrix or array, with data structure that says if it has flashed this turn

        val octopusGrid = input.flatMapIndexed { y, line -> line.mapIndexed { x, it -> Point(x, y) to it.digitToInt() } }.toMap().toMutableMap()
        var totalFlashes = 0

        fun doAFlash(point: Point) {
            totalFlashes++
            octopusGrid[point] = 0
            point.getAdjacent().mapNotNull {
                if (octopusGrid[it] != null)
                    it
                else
                    null
            }.forEach {
                val adjacentPoint = octopusGrid[it]!!
                if (adjacentPoint != 0) {
                    octopusGrid[it] = adjacentPoint + 1
                }
            }
        }

        repeat(10000){
            println(it)
            octopusGrid.entries.forEach {
                octopusGrid[it.key] = it.value + 1
            }
            var flashesThisTime = 0
            while (octopusGrid.entries.filter { it.value > 9 }.size > 0) {
                octopusGrid.entries.filter { it.value > 9 }.forEach {
                    doAFlash(it.key)
                    flashesThisTime++
                }
            }
            if (flashesThisTime == 100) {
                return 0
            }
        }

        return totalFlashes
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    //check(part1(testInput) == 1656)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

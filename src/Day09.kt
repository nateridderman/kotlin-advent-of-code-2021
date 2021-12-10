import java.lang.Integer.min

fun main() {

    fun <T>List<List<T>>.rowToColumn() = (0 until first().size).map{row -> (0 until size).map {col-> this[col][row]  }}

    fun part1(input: List<String>): Int {
        val rows = input.map {
            val paddedRow = it.toMutableList()
            paddedRow.add(0, '9')
            paddedRow.add('9')
            paddedRow.toCharArray().map { it.digitToInt() }
        }.toMutableList()
        rows.add(0, List(rows[1].size, { 9 }))
        rows.add(List(rows[1].size, { 9 }))

        val columns = rows.rowToColumn()

        return rows.subList(1, rows.size - 1).mapIndexed { y, row ->
            row.subList(1, row.size - 1).mapIndexed { x, i ->
//                println(
//                    "$i ${rows[y+1][x]} ${rows[y+1][x+2]} ${columns[x+1][y]} ${columns[x+1][y+2]}"
//                )
                val nearby = listOf(rows[y+1][x], rows[y+1][x+2], columns[x+1][y], columns[x+1][y+2])
                if (i < nearby.minOf { it }) {
                    i+1
                } else {
                    0
                }
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val rows = input.map {
            val paddedRow = it.toMutableList()
            paddedRow.add(0, '9')
            paddedRow.add('9')
            paddedRow.toCharArray().map { it.digitToInt() }
        }.toMutableList()
        rows.add(0, List(rows[1].size, { 9 }))
        rows.add(List(rows[1].size, { 9 }))

        val columns = rows.rowToColumn()

        val lowPoints = rows.subList(1, rows.size - 1).mapIndexed { y, row ->
            row.subList(1, row.size - 1).mapIndexedNotNull { x, i ->
                val nearby = listOf(rows[y+1][x], rows[y+1][x+2], columns[x+1][y], columns[x+1][y+2])
                if (i < nearby.minOf { it }) {
                    Pair(x,y)
                } else {
                    null
                }
            }
        }.flatten()

        fun getValueAtPoint(x: Int, y: Int) : Int {
            return rows[y+1][x+1]
        }

        fun anyNeighborsNotNine(point: Pair<Int, Int>) : List<Pair<Int, Int>> {
            val result = mutableListOf<Pair<Int, Int>>()
            val (x,y) = point

            sequenceOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                .map { (dx, dy) -> x + dx to y + dy }
                .map {
                    getValueAtPoint(it.first, it.second)
                }
            if (getValueAtPoint(x-1, y) < 9) {
                result.add(Pair(x-1, y))
            }
            if (getValueAtPoint(x+1, y) < 9) {
                result.add(Pair(x+1, y))
            }
            if (getValueAtPoint(x, y-1) < 9) {
                result.add(Pair(x, y-1))
            }
            if (getValueAtPoint(x, y+1) < 9) {
                result.add(Pair(x, y+1))
            }
            return result
        }

        //for each low point, recursively find new neighbors that aren't nine
        val basinSizes = lowPoints.map {
            val basin = mutableListOf<Pair<Int, Int>>()
            basin.add(it)
            var foundAnother = true
            while (foundAnother) {
                foundAnother = false
                basin.firstOrNull {
                    val newNeighbor = anyNeighborsNotNine(it).firstOrNull {
                        !basin.contains(it)
                    }
                    if (newNeighbor != null) {
                        basin.add(newNeighbor)
                        foundAnother = true
                        true
                    } else {
                        false
                    }
                }
            }
            basin.size
        }
        return basinSizes.sortedDescending().take(3).reduce { acc, i ->  acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

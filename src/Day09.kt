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
        //return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
//    println(part1(testInput))


    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}


fun main() {
    fun part1(input: List<String>): Int {
        var origSeq = input[0]
        val rules = input.takeLast(input.size-2).map {
            it.substringBefore(" -> ") to it.substringAfter(" -> ")
        }.toMap()

        //5 steps
        repeat(10) {
            origSeq = origSeq.toCharArray().toList().zipWithNext().map {
                it.first.toString() + it.second
            }.map {
                it[0] + rules[it]!!
            }.joinToString("").plus(origSeq.last())
        }

        val buckets = origSeq.toCharArray().groupBy { it }.map { it.key to it.value.size }.toMap()
        return buckets.entries.maxOf { it.value } - buckets.entries.minOf { it.value }
    }

    fun part2(input: List<String>): Long {
        val origSeq = input[0]
        val rules = input.takeLast(input.size - 2).map {
            it.substringBefore(" -> ") to it.substringAfter(" -> ")
        }.toMap()

        var pairBuckets = origSeq.toCharArray().toList().zipWithNext().map {
            it.first.toString() + it.second
        }.groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap()

        repeat(40) {
            pairBuckets = pairBuckets.entries.toList().flatMap {
                listOf(
                    it.key[0] + rules[it.key]!! to it.value,
                    rules[it.key]!! + it.key[1] to it.value
                )
            }.groupBy { it.first }.map { it.key to it.value.sumOf { it.second } }.toMap()
        }

        val buckets = pairBuckets.entries.groupBy { it.key[0] }.map { it.key to it.value.sumOf { it.value } }.toMap().toMutableMap()
        buckets[origSeq.last()] = buckets[origSeq.last()]!! + 1
        return buckets.entries.maxOf { it.value } - buckets.entries.minOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

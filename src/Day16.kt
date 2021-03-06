import java.util.*

fun main() {

    var totalVersion = 0

    fun popChars(x: Int, bits: LinkedList<Char>): String {
        var result = ""
        repeat(x) {
            result += bits.pop()
        }
        return result
    }

    fun parseLiteral(bits: LinkedList<Char>): Long {
        var numberDigits = String()
        while (bits.pop() == '1') {
            numberDigits += popChars(4, bits)
        }
        numberDigits += popChars(4, bits)
        return numberDigits.toLong(2)
    }

    //TODO for v2, need to pass in a mutable FIFO queue so we can pop off chars and not have to return it
    fun parsePacket(bits: LinkedList<Char>): Long {
        val version = popChars(3, bits).toInt(2)
        totalVersion += version
        val typeId = popChars(3, bits).toInt(2)
        if (typeId == 4) {
            return parseLiteral(bits)
        } else {
            val lengthTypeId = bits.pop()
            val resultsForOperator = mutableListOf<Long>()
            if (lengthTypeId == '0') {
                //then the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
                val bitsInSubPackets = popChars(15, bits).toInt(2)
                val expectedSize = bits.size - bitsInSubPackets
                while (bits.size > expectedSize) {
                    resultsForOperator.add(parsePacket(bits))
                }
            } else { //eq '1'
                //then the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
                val numOfSubPackets = popChars(11, bits).toInt(2)
                repeat(numOfSubPackets) {
                    resultsForOperator.add(parsePacket(bits))
                }
            }

            if (typeId == 0) {
                return resultsForOperator.sum()
            } else if (typeId == 1) {
                return resultsForOperator.fold(1) {
                        acc, c -> acc * c
                }
            } else if (typeId == 2) {
                return resultsForOperator.minOf { it }
            } else if (typeId == 3) {
                return resultsForOperator.maxOf { it }
            } else if (typeId == 5) {
                return if (resultsForOperator[0] > resultsForOperator[1]) 1 else 0
            } else if (typeId == 6) {
                return if (resultsForOperator[0] < resultsForOperator[1]) 1 else 0
            } else if (typeId == 7) {
                return if (resultsForOperator[0] == resultsForOperator[1]) 1 else 0
            } else {
                return -1213321321312
            }
        }
    }

    fun part1(input: List<String>): Int {
        totalVersion = 0
        val bits = input[0].toCharArray()
            .map { it.digitToInt(16).toString(2).padStart(4, '0')}
            .joinToString("")
        val bitsList = LinkedList<Char>()
        bitsList.addAll(bits.toCharArray().toList())
        parsePacket(bitsList)
        return totalVersion
    }

    fun part2(input: List<String>): Long {
        totalVersion = 0
        var bits = input[0].toCharArray()
            .map { it.digitToInt(16).toString(2).padStart(4, '0')}
            .joinToString("")
        var bitsList = LinkedList<Char>()
        bitsList.addAll(bits.toCharArray().toList())
        return parsePacket(bitsList)
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day16_test")
    check(part1(testInput) == 6)
    testInput = readInput("Day16_test1")
    check(part1(testInput) == 16)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}

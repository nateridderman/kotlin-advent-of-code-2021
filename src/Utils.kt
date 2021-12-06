import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

//private fun readInt() = readln().toInt() // single int
//private fun readStrings() = readln().split(" ") // list of strings
//private fun readInts() = readStrings().map { it.toInt() } // list of ints

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

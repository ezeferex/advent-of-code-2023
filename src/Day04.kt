import kotlin.math.pow

fun String.isDigit() = this.isNotBlank() && this.all { it.isDigit() }

fun getAmountWinNumbers(input: String): Int {
    val allNumbers = input.substringAfter(": ")
        .split(" | ")
        .map { word -> word.split(" ").filter { num -> num.isDigit() } }
    val winNumbers = allNumbers[0]
    val numbers = allNumbers[1]
    return winNumbers.count { num -> numbers.map { it.toInt() }.contains(num.toInt()) }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val amountWinNumbers = getAmountWinNumbers(it)
            2.0.pow(amountWinNumbers - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val scratchcards = IntArray(input.size) { 1 }
        input.forEachIndexed { index, numbers ->
            val amountWinNumbers = getAmountWinNumbers(numbers)
            repeat (amountWinNumbers) {
                scratchcards[index + it + 1] += scratchcards[index]
            }
        }
        return scratchcards.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    check(part1(testInput) == 13)
    part1(input).println()
    check(part2(testInput) == 30)
    println("Result of part 2 is: ")
    part2(input).println()
}

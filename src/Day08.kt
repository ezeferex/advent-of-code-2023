fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    val input = readInput("Day08")
    check(part1(testInput) == 2)
    check(part1(testInput2) == 6)
    part1(input).println()


    check(part2(testInput) == 0)
    part2(input).println()
}

fun main() {

    fun distanceTravelled(raceTime: Int, holdTime: Int) : Int = (raceTime - holdTime) * holdTime

    fun distanceTravelledLong(raceTime: Long, holdTime: Long) : Long = (raceTime - holdTime) * holdTime

    fun part1(input: List<String>): Int {
        val time = input
            .first()
            .substringAfter(':')
            .trim()
            .split(' ')
            .filter { it.isDigit() }
            .map(String::toInt)

        val distance = input
            .last()
            .substringAfter(':')
            .trim()
            .split(' ')
            .filter { it.isDigit() }
            .map(String::toInt)

        var count = 1
        distance.forEachIndexed { index, item ->
            var countBeat = 0
            for (i in 0..time[index]) {
                if (distanceTravelled(time[index], i) > item) {
                    countBeat++
                }
            }
            count*=countBeat
        }

        return count
    }

    fun part2(input: List<String>): Long {
        val time = input
            .first()
            .substringAfter(':')
            .trim()
            .split(' ')
            .filter { it.isDigit() }
            .reduce { acc, c -> acc + c }
            .toLong()

        val distance = input
            .last()
            .substringAfter(':')
            .trim()
            .split(' ')
            .filter { it.isDigit() }
            .reduce { acc, c -> acc + c }
            .toLong()

        var countBeat = 0L
        for (i in 0..time) {
            if (distanceTravelledLong(time, i) > distance) {
                countBeat++
            }
        }

        return countBeat
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")
    check(part1(testInput) == 288)
    part1(input).println()

    check(part2(testInput) == 71503L)
    part2(input).println()
}
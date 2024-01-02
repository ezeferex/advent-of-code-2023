const val SEEDS = "seeds: "

fun getCategoryNum(ranges: List<Triple<Long, Long, Long>>, numb: Long): Long {
    var aux = numb
    for (range in ranges) {
        val sourceStart = range.second
        val sourceDestination = range.first
        val rangeLength = range.third
        if (numb in sourceStart..sourceStart + rangeLength) {
            aux = numb + (sourceDestination - sourceStart)
            break
        }
    }
    return aux
}

fun String.getRanges() : Triple<Long, Long, Long> {
    val ranges = this.split(" ").map(String::toLong)
    return Triple(ranges[0], ranges[1], ranges[2])
}

fun generateAlmanac(input: List<String>): List<List<Triple<Long, Long, Long>>> = input.joinToString("\n")
    .split("\n\n")
    .map {
        it.substringAfter(" map:")
            .split("\n")
            .filter(String::isNotEmpty)
            .map(String::getRanges)
    }

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input[0]
            .substringAfter(SEEDS)
            .split(' ')
            .map(String::toLong)

        val almanac = generateAlmanac(input.takeLast(input.size - 2))

        var minLocation = 0L
        return seeds.minOf { numb ->
            minLocation = numb
            almanac.map {
                minLocation = getCategoryNum(it, minLocation)
                minLocation
            }.last()
        }
    }

    fun part2(input: List<String>): Long {
        val seeds = input.first().substringAfter(":").trim().split(" ")
            .map { it.toLong() }.chunked(2).map {
                it.first()..<it.first() + it.last()
            }

        val almanac = generateAlmanac(input.takeLast(input.size - 2))

        return generateSequence(0L, Long::inc).first { location ->
            val seed = almanac.fold(location) { acc, ranges ->
                getCategoryNum(ranges, acc)
            }
            seeds.any { seedRange -> seed in seedRange }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    check(part1(testInput) == 35L)
    part1(input).println()

    println("The result is:")
//    check(part2(testInput) == 46L)
    part2(testInput).println()
}

//if (79 in 50..50 + 48) { 79 + abs(52 - 50) }
//
//50 98 2
//52 50 48
//
//Destination start of soil : 50
//Source range start of seed: 98
//Range length: 2
//98 | 50
//99 | 51
//Destination start of soil : 52
//Source range start of seed: 50
//Range length: 48
//50 | 52
//51 | 53
//
//Destination start of fertilizer : 0
//Source range start of soil: 15
//Range length: 37
//[15..51], [52..53], [0..14], [54..]
//[0..36], [37, 38], [0..14], [54..]
//15 | 0
//16 | 1
//...
//51 | 36
//
//Destination start of fertilizer : 37
//Source range start of soil: 52
//Range length: 2
//52 | 37
//53 | 38
//
//Destination start of fertilizer : 39
//Source range start of soil: 0
//Range length: 15
//0 | 39
//1 | 40
//...
//14 | 53
//
//
//Seeds: 79
//Soil: 81

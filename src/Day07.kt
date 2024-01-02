
val ORDER_CARDS_PART_1 = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

val ORDER_CARDS_PART_2 = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

enum class Variants(val points: Int) {
    FIVE_OF_KIND(6),
    FOUR_OF_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    companion object {
        fun from(card: String): Variants {

            var cardVariants = card.parseCard().toMutableList()

            // check if it is a J there
            // filter the J
            // sort card Variants by amount and card
            // update the list adding the amount of j with first element
            val jCard = cardVariants.firstOrNull{ it.first == 'J' }
            if (cardVariants.size > 1 && jCard != null) {
                cardVariants = cardVariants
                    .filter { it.first != 'J'}
                    .sortedWith { item1, item2 ->
                        if (item1.second > item2.second) {
                            -1
                        } else if (item1.second < item2.second) {
                            1
                        } else {
                            val posCurrentCard = ORDER_CARDS_PART_2.reversed().indexOf(item1.first)
                            val posNextCard = ORDER_CARDS_PART_2.reversed().indexOf(item2.first)
                            posNextCard - posCurrentCard
                        }
                    }.toMutableList()

                cardVariants[0] = Pair(cardVariants.first().first, cardVariants.first().second + jCard.second)
            }

            return when (cardVariants.maxOf { it.second }) {
                5 -> FIVE_OF_KIND
                4 -> FOUR_OF_KIND
                3 -> if (cardVariants.size == 2) FULL_HOUSE else THREE_OF_KIND
                2 -> if (cardVariants.size == 3) TWO_PAIR else ONE_PAIR
                else -> HIGH_CARD
            }
        }
    }
}

fun String.parseCard(): List<Pair<Char, Int>> {
    return this.fold(emptyList<Pair<Char, Int>>().toMutableList()) { list, item ->
        val handCard = list.firstOrNull { it.first == item }
        if (handCard != null) {
            list[list.indexOf(handCard)] = Pair(item, handCard.second + 1)
        } else {
            list.add(Pair(item, 1))
        }
        list
    }
}

fun String.getListOf(): List<String> = this.split(" ")
        .filterNot { it.isEmpty() }

fun main() {

    fun String.compareCard(otherCard: String, array: List<Char>): Int {
        val currentVariant = Variants.from(this)
        val otherVariant = Variants.from(otherCard)

//        println("The card $this has ${currentVariant.name}")

        val resultVariant = currentVariant.points - otherVariant.points
        return if (resultVariant == 0) {
            var order = 0
            for (i in this.indices) {
                val posCurrentCard = array.reversed().indexOf(this[i])
                val posNextCard = array.reversed().indexOf(otherCard[i])
                if (posCurrentCard != posNextCard) {
                    order = posCurrentCard - posNextCard
                    break
                }
            }
            order
        } else {
            resultVariant
        }
    }

    fun Set<Pair<String, String>>.orderMatches(array: List<Char>) =
        this.sortedWith { item1, item2 ->
            item1.first.compareCard(item2.first, array)
        }

    fun part1(input: List<String>): Int {
        val matches = input.map {
            val hand = it.getListOf()
            Pair(hand.first(), hand.last())
        }.toSet().orderMatches(ORDER_CARDS_PART_1)

        return matches.foldIndexed(0) { index, acc, next ->
            acc + next.second.toInt() * (index + 1)
        }
    }

    fun part2(input: List<String>): Int {
        val matches = input.map {
            val hand = it.getListOf()
            Pair(hand.first(), hand.last())
        }.toSet().orderMatches(ORDER_CARDS_PART_2)

        return matches.foldIndexed(0) { index, acc, next ->
            acc + next.second.toInt() * (index + 1)
        }
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
     check(part1(testInput) == 6440)
     part1(input).println()

    check(part2(testInput) == 5905)
    part2(input).println()
}

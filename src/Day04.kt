import kotlin.math.pow

private data class ScratchCard(
        val id: Int,
        val winningNumbers: Set<Int>,
        val cardNumbers: Set<Int>
) {
    val matchingNumbers: Int = winningNumbers.intersect(cardNumbers).count()
}

object Day04 : AdventOfCodeSolver(day = 4) {
    override fun part1(input: List<String>): String {
        val scratchCards = parse(input)
        var sum = 0
        for (card in scratchCards) {
            sum += 2f.pow(card.matchingNumbers - 1).toInt()
        }
        return sum.toString()
    }

    override fun part2(input: List<String>): String {
        val scratchCards = parse(input)
        val cardCopyCount = IntArray(scratchCards.size) { 1 }
        for ((i, card) in scratchCards.withIndex()) {
            val matchCount = card.matchingNumbers
            if (matchCount == 0) continue
            for (j in i + 1..i + matchCount) {
                cardCopyCount[j] += cardCopyCount[i]
            }
        }
        return cardCopyCount.reduce { a, b -> a + b }.toString()
    }

    private fun parse(input: List<String>): List<ScratchCard> = input.map { card ->
        val id = Regex("Card\\s+(\\d+)").find(card)!!.groupValues[1].toInt()
        val allNumbers = card.split(':')[1].split('|').map {
            it.trim().split("\\s+".toRegex()).map { num -> num.toInt() }.toSet()
        }
        ScratchCard(id, allNumbers[0], allNumbers[1])
    }
}

fun main() {
    Day04.solve("13", "30")
}
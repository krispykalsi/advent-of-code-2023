class NumberTrie : Trie(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "zero",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
) {
    private val wordToIntMappings: Map<String, Int> = hashMapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
            "zero" to 0,
    )

    fun findNumber(string: String): Int? {
        var node: Node? = root
        var word = ""
        for (ch in string) {
            node = node?.getChild(ch)
            if (node == null || node.value != ch) {
                return null
            }
            word += node.value
            if (node.isWordEnd()) {
                return parseWordOrDigitToNumber(word)
            }
        }
        return null
    }

    private fun parseWordOrDigitToNumber(wordOrDigit: String): Int {
        return wordToIntMappings[wordOrDigit] ?: wordOrDigit.toInt()
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            var firstNum = ' '
            var lastNum = ' '
            for (ch in line) {
                if (!ch.isDigit()) {
                    continue
                }
                if (firstNum == ' ') {
                    firstNum = ch
                }
                lastNum = ch
            }
            sum += "$firstNum$lastNum".toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val numberTrie = NumberTrie()

        var sum = 0
        for (line in input) {
            var firstNum = -1
            var lastNum = -1
            for (i in line.indices) {
                val num = numberTrie.findNumber(line.substring(i)) ?: continue
                if (firstNum == -1) {
                    firstNum = num
                }
                lastNum = num
            }

            sum += "$firstNum$lastNum".toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

open class Trie(vararg words: String) {
    class Node(val value: Char, private var isWord: Boolean = false) {
        private val children = mutableMapOf<Char, Node>()

        fun addChild(char: Char): Node {
            if (!children.containsKey(char)) {
                children[char] = Node(char)
            }
            return children[char]!!
        }

        fun hasChild(char: Char): Boolean {
            return children.containsKey(char)
        }

        fun getChild(char: Char): Node? {
            return children[char]
        }

        fun isWordEnd(): Boolean {
            return isWord
        }

        fun markWordEnd() {
            isWord = true
        }
    }

    protected val root: Node = Node(' ')

    init {
        words.forEach { insert(it) }
    }

    fun insert(word: String) {
        var node = root
        for (char in word) node = node.addChild(char)
        node.markWordEnd()
    }

    fun search(word: String): Boolean {
        var node = root
        for (char in word) {
            if (!node.hasChild(char)) {
                return false
            }
            node = node.getChild(char)!!
        }
        return node.isWordEnd()
    }

}

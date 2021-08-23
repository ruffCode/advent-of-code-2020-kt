import java.math.BigInteger

object Day03 {

    private val input = PuzzleInput("day03.txt").readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        val vectors = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        println(partOne(input, vectors.last()))
        println(partOneCleaner(input, 1 to 2))
        println(partTwo(input, vectors))
        println(partTwoCleaner(input, vectors))
    }

    internal fun partOne(field: List<String>, vector: Vector): Int {
        var count = 0
        var pos = 0

        val expandedField = field.map { s ->
            val builder = StringBuilder()
            repeat(field.size * 32) {
                builder.append(s)
            }
            builder.toString()
        }
        for (i in expandedField.indices step vector.second) {
            if (i != 0) {
                pos += vector.first
                if (expandedField[i][pos] == '#') {
                    count++
                }
            }
        }
        return count
    }

    internal fun partTwo(field: List<String>, vectors: List<Vector>): BigInteger =
        vectors.map { partOne(field, it).toBigInteger() }.reduce { a, b -> a * b }

    internal fun partOneCleaner(field: List<String>, vector: Vector): Int {
        val (dx, dy) = vector
        val width = field.first().length
        val count = field.indices.count { y ->
            y % dy == 0 && field[y][y / dy * dx % width] == '#'
        }
        return count
    }

    internal fun partTwoCleaner(field: List<String>, vectors: List<Vector>): BigInteger =
        vectors.map { partOneCleaner(field, it).toBigInteger() }.reduce { a, b -> a * b }
}

internal typealias Vector = Pair<Int, Int>

object Day01 {

    private val input = PuzzleInput("day01.txt").readLines().map { it.toInt() }

    internal fun partOne(input: List<Int>): Int {
        var result = 0
        outer@ for (i in input) {
            for (ii in input) {
                if (i + ii == 2020) {
                    result = i * ii
                    break@outer
                }
            }
        }
        return result
    }

    internal fun partTwo(input: List<Int>): Int {
        var result = 0
        outer@ for (i in input) {
            for (ii in input) {
                for (iii in input) {
                    if (i + ii + iii == 2020) {
                        result = i * ii * iii
                        break@outer
                    }
                }
            }
        }
        return result
    }

    internal fun partOneBetter(numbers: List<Int>): Int? {
        val pair = numbers.findPairOfSum(2020)
        return pair?.let { (x, y) -> x * y }
    }

    internal fun partTwoBetter(numbers: List<Int>): Int? {
        val triple = numbers.findTripleOfSum(2020)
        return triple?.let { (x, y, z) -> x * y * z }
    }

    private fun List<Int>.findTripleOfSum(sum: Int): Triple<Int, Int, Int>? =
        firstNotNullOfOrNull { x ->
            findPairOfSum(sum - x)?.let {
                Triple(x, it.first, it.second)
            }
        }

    private fun List<Int>.findPairOfSum(
        sum: Int,
    ): Pair<Int, Int>? {
        val complements = associateBy { sum - it }

        return firstNotNullOfOrNull { number ->
            complements[number]?.let {
                Pair(number, it)
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(partOne(input))
        println(partOneBetter(input))
        println(partTwo(input))
        println(partTwoBetter(input))
    }
}

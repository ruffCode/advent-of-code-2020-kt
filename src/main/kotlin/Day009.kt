import PuzzleInput.toStingList

object Day009 {
    private val target = 556543474L
    val testInput = """
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent().split(newLine).map { it.toLong() }

    val input = PuzzleInput("day009.txt").toStingList().map { it.trim().toLong() }

    @JvmStatic
    fun main(args: Array<String>) {
        println(
            measureTimedValueMinOf {
                partOne()
            }
        )
        println(
            measureTimedValueMinOf {
                partOneNicer()
            }
        )

        println(
            measureTimedValueMinOf {
                partTwo()
            }
        )

        println(
            measureTimedValueMinOf {
                partTwoSmallerArray()
            }
        )
    }

    internal fun partOne(): Long? = input.findInvalidNumber(25)

    private fun partOneNicer(): Long? = input.findInvalidNumberNicer(25)

    internal fun partTwo(): Long {
        val result = input.findSublist(target)
        return result.minMaxSum()
    }

    private fun partTwoSmallerArray(): Long {
        val idx = input.indexOfFirst { it >= target }

        val smaller = input.subList(0, idx)
        val result = smaller.findSublist(target)

        return result.minMaxSum()
    }

    internal fun List<Long>.findInvalidNumber(preambleSize: Int = 5): Long? {
        val nums = this

        var result: Long? = null
        for (i in preambleSize until nums.size - preambleSize) {
            val pre = nums.subList(i - preambleSize, i)
            val num = nums[i]
            if (num !in pre.allSums()) {
                result = num
                break
            }
        }
        return result
    }

    private fun List<Long>.allSums(): Set<Long> {
        return flatMap {
            this.filter { v -> v != it }.map { v -> v + it }
        }.toSet()
    }

    // all credit to  Svetlana Isakova
    private fun List<Long>.hasPairOfSum(sum: Long): Boolean =
        indices.any { i ->
            indices.any { j ->
                i != j && this[i] + this[j] == sum
            }
        }

    // all credit to  Svetlana Isakova
    internal fun List<Long>.findSublist(sum: Long) =
        (2..size).firstNotNullOf { sublistSize ->
            asSequence()
                .windowed(sublistSize)
                .firstOrNull { sublist ->
                    sublist.sum() == sum
                }
        }

    // all credit to  Svetlana Isakova
    internal fun List<Long>.findInvalidNumberNicer(preambleSize: Int = 5): Long? =
        ((preambleSize + 1)..lastIndex)
            .firstOrNull { index ->
                val previousGroup = subList(index - preambleSize, index)
                !previousGroup.hasPairOfSum(this[index])
            }
            ?.let { this[it] }

    internal fun List<Long>.minMaxSum(): Long = this.minOf { it } + this.maxOf { it }
}

import Day009.findInvalidNumber
import Day009.findInvalidNumberNicer
import Day009.findSublist
import Day009.minMaxSum
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.longs.shouldBeExactly

class Day009Test : FunSpec({
    val target = 556543474L
    val puzzle = Day009
    val badInput = """
        90999
        988733
        djjas
        23213
    """.trimIndent()

    context("part one") {
        test("fails to parse invalid input") {
            shouldThrowExactly<NumberFormatException> {
                badInput.split(newLine).map { it.trim().toLong() }
            }
        }
        test("parses valid input") {
            shouldNotThrowAny {
                puzzle.input
            }
        }
        test("returns a number that is not the sum of 25 any preceding numbers") {
            (puzzle.input.findInvalidNumber(25) ?: 0L) shouldBeExactly target
        }

        test("returns a number that is not the sum of 25 any preceding numbers [nicer]") {
            (puzzle.input.findInvalidNumberNicer(25) ?: 0L) shouldBeExactly target
        }
    }

    context("part two") {

        test(
            """returns the sum of smallest and largest of contiguous 
            |set of numbers that add up to target Long""".trimMargin()
        ) {
            val one = puzzle.testInput.findSublist(127L)
            val two = puzzle.input.findSublist(target)

            one.size shouldBeExactly 4
            two.size shouldBeExactly 17

            one.minMaxSum() shouldBeExactly 62
            two.minMaxSum() shouldBeExactly 76096372L
        }
        test(
            """returns the sum of smallest and largest of contiguous set of numbers 
            |that add up to target Long [smaller array]""".trimMargin()
        ) {

            val idx = puzzle.input.indexOfFirst { it >= target }

            val smaller = puzzle.input.subList(0, idx)
            val result = smaller.findSublist(target)

            result.minMaxSum() shouldBeExactly 76096372L
        }
    }
})

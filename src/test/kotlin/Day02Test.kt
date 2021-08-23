import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day02Test : FunSpec({

    val puzzle = Day02
    val input = """
        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
    """.trimIndent().reader().readLines()

    test("partOne") {
        puzzle.partOne(input) shouldBeExactly 2
    }

    test("partOneBetter") {
        puzzle.partOneBetter(input) shouldBeExactly 2
    }

    test("partTwo") {
        puzzle.partTwo(input) shouldBeExactly 1
    }

    test("partTwoBetter") {
        puzzle.partTwoBetter(input) shouldBeExactly 1
    }
})

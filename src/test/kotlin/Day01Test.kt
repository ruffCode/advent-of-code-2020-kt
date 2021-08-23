import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Day01Test : FunSpec({

    val puzzle = Day01
    val input = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent().reader().readLines().map(String::toInt)

    test("partOne") {
        puzzle.partOne(input) shouldBeExactly 514579
    }

    test("partOneBetter") {
        puzzle.partOneBetter(input) shouldBe 514579
    }

    test("partTwo") {
        puzzle.partTwo(input) shouldBeExactly 241861950
    }

    test("partTwoBetter") {
        puzzle.partTwoBetter(input) shouldBe 241861950
    }
})

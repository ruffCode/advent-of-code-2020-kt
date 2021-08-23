import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Day003Test : ShouldSpec({

    val puzzle = Day003
    val input = """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent().reader().readLines()
    val vectors = listOf(3 to 1, 1 to 1, 5 to 1, 7 to 1, 1 to 2)

    context("Part one") {
        context("My solution") {
            should("return correct number of trees") {
                puzzle.partOne(input, vectors.first()) shouldBe 7
            }
        }
        context("Cleaned up solution") {
            should("return correct number of trees") {
                puzzle.partOneBetter(input, vectors.first()) shouldBe 7
            }
        }
    }
    context("Part two") {
        context("My solution") {
            should("return correct number of trees") {
                puzzle.partTwo(input, vectors) shouldBe BigInteger.valueOf(336)
            }
        }
        context("Cleaned up solution") {
            should("return correct number of trees") {
                puzzle.partTwoBetter(input, vectors) shouldBe BigInteger.valueOf(336)
            }
        }
    }
})

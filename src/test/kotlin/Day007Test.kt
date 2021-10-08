import Day007.toBags
import Day007.total
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day007Test : FunSpec({

    val puzzle = Day007

    test("should return the correct number of bags") {
        puzzle.partOne() shouldBeExactly 335
    }
    test("should return total number of bags") {

        puzzle.testInputPart2.toBags().total("shiny gold") shouldBeExactly 126
    }
    test("should return total number of bags2") {

        puzzle.testInput.toBags().total("shiny gold") shouldBeExactly 32
    }

    test("should return total bags") {
        puzzle.partTwo() shouldBeExactly 2431
    }
})

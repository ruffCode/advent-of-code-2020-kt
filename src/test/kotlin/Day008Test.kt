import Day008.toInstructions
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day008Test : FunSpec({

    val puzzle = Day008
    val testInstructions = puzzle.testInput.toInstructions()

    context("part one") {
        test("parses input") {
            val noOps = testInstructions.filterIsInstance<Instruction.NOp>()
            noOps.size shouldBeExactly 1

            val acc = testInstructions.filterIsInstance<Instruction.Acc>()

            acc[1].argument shouldBeExactly 3
            acc[2].argument shouldBeExactly -99
        }

        test("returns correct accumulator value") {
            puzzle.partOne() shouldBeExactly 1818
        }
    }
    context("part two") {
        test("returns correct accumulator value") {
            puzzle.partTwo() shouldBeExactly 631
        }
    }
})

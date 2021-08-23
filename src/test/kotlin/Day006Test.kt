import Day006.getGroups
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldNotBeExactly

class Day006Test : FunSpec({
    val puzzle = Day006
    val input = """
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent()

    val input1 = PuzzleInput("day006.txt").readText()

    val input2 = PuzzleInput("day006-alt.txt").readText()

    val sample2 = """
        ysjircxtgfzpb
        ynsxpgtcifz
        riydpzsfxutcg
        gsyitzdvpfcrox
        yclxfzietsmghwp
    """.trimIndent()
    context("Part one") {
        test("parse input in to groups") {
            val groups = input.reader().readText().trim().getGroups()
            groups.size shouldBeExactly 5
            groups[2].replace(newLine, "").length shouldBeExactly 4
        }
        test("get yeses for group") {
            "aaaa".toSet().size shouldBeExactly 1
            "abc".toSet().size shouldBeExactly 3
        }

        test("return correct sum of yeses for all groups") {
            puzzle.partOne(input) shouldBeExactly 11
        }
    }

    context("Part two") {

        context("Group with single person") {
            test("should handle group with one person") {
                puzzle.getSecondAnswerWrong(listOf("abc")) shouldBeExactly 3
                puzzle.getSecondAnswerWrong(listOf("b")) shouldBeExactly 1
            }
        }
        context("Group with multiple people") {
            test("all have the same answer") {
                puzzle.getSecondAnswerWrong(listOf("a", "a", "a", "a")) shouldBeExactly 1
            }
            test("all have different answers") {
                puzzle.getSecondAnswerWrong(listOf("a", "b", "c")) shouldBeExactly 0
            }
            test("intersecting answers") {
                puzzle.getSecondAnswerWrong(listOf("ab", "ac")) shouldBeExactly 1
                puzzle.getSecondAnswerWrong(listOf("abc", "ac")) shouldBeExactly 2
                puzzle.getSecondAnswerWrong(listOf("o", "o", "o", "g", "o")) shouldBeExactly 0
            }
        }
        test("return correct sum of yeses for all groups") {
            puzzle.partTwoWrong(input) shouldBeExactly 6
            puzzle.partTwoWrong(sample2) shouldBeExactly 10
            puzzle.partTwoWrong(input2) shouldBeExactly 3254
            puzzle.partTwoCorrect(input2) shouldBeExactly 3254
        }

        context("Shows there's an edge-case getSecondAnswerWrong is not catching ") {
            test("outputs equal when using input2") {
                val correct = 3254
                puzzle.partTwoWrong(input2) shouldBeExactly correct
                puzzle.partTwoCorrect(input2) shouldBeExactly correct
            }

            test("outputs do not equal when using input1") {
                puzzle.partTwoCorrect(input1) shouldNotBeExactly puzzle.partTwoWrong(input1)
            }
        }
    }
})

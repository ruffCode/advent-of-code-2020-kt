import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Day007Test : FunSpec({

    val puzzle = Day007

    test("should return the correct number of bags"){
        puzzle.partOne() shouldBeExactly 335
    }

})

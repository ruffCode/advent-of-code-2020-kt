import Day004.getPassports
import Day004.getPassportsBetter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Day004Test : ShouldSpec() {

    private val puzzle = Day004

    private val invalidPassports = """
        eyr:1972 cid:100
        hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

        iyr:2019
        hcl:#602927 eyr:1967 hgt:170cm
        ecl:grn pid:012533040 byr:1946

        hcl:dab227 iyr:2012
        ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

        hgt:170cm ecl:grn
        eyr:2038 hcl:#602927 iyr:2010
        pid:999 byr:1992
    """.trimIndent()

    private val validPassports = """
        pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
        hcl:#623a2f

        eyr:2029 ecl:blu cid:129 byr:1989
        iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

        hcl:#888785
        hgt:164cm byr:2001 iyr:2015 cid:88
        pid:545766238 ecl:hzl
        eyr:2022

        iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
    """.trimIndent()

    private val allPassports = validPassports + "\n\n" + invalidPassports
    val input = PuzzleInput("day004-sample.txt")

    init {
        context("Part One") {

            context("My solution") {
                should("return number of passports not missing any fields other than cid") {
                    input.readLines().getPassports().size shouldBe 2
                }
            }
            context("Alternate solution") {
                should("return number of passports not missing any fields other than cid") {
                    input.readText().getPassportsBetter().size shouldBe 2
                }
            }
        }

        context("Part Two") {
            context("Validation") {
                val passports = invalidPassports.reader().readLines().getPassports()
                should("fail due to invalid height") {
                    testException("Height") {
                        Passport.fromPassportMap(passports.first())
                    }
                }
                should("fail due to invalid Exp year") {
                    testException("Exp year") {
                        Passport.fromPassportMap(passports[1])
                    }
                }
                should("fail due to invalid hair color") {
                    testException("Hair color") {
                        Passport.fromPassportMap(passports[2])
                    }
                }
                should("fail due to invalid passport ID") {
                    testException("Passport ID") {
                        Passport.fromPassportMap(passports[3])
                    }
                }

                validPassports.reader().readLines().getPassports().forEachIndexed { index, map ->
                    should("pass validations $index") {
                        Passport.fromPassportMap(map)
                    }
                }
            }
            context("My solution") {
                should("return the correct number of valid passports") {
                    puzzle.checkPassport(allPassports.reader().readLines()) shouldBe 4
                }
            }
            context("Alternate solution") {
                should("return the correct number of valid passports") {
                    puzzle.checkPassportsBetter(allPassports.reader().readText()) shouldBe 4
                }
            }
        }
    }
}

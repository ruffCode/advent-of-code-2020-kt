# Advent of Code 2020 Kotlin Solutions

### Just messing around... solving puzzles

My process is to solve the puzzles then check [Kotlin Hands-On Advent of Code 2020](https://github.com/kotlin-hands-on/advent-of-code-2020)
for a better/cleaner solution and incorporate it. I leave in my initial, often messier, solutions in.


### Day 6 notes

This was an adventure! My part two solution was overcomplicated and was not producing the correct result.
Due to the nature of input to this problem, testing for a fair number of edge cases is almost impossible as it is
very time intensive to manually calculate the correct output.

I was starting to think there was an issue with my input, so I decided to run the Kotlin Hands-On repo's input against
my solution as well as running their solution and the outputs matched. However, when running my input against their 
solution, there was a difference of 2  (3141 vs 3143).

I would have loved to figure out what was wrong with my code, or rather what was technically wrong with my code. In reality 
code was wrong because it was overcomplicated and relied  on every edge case being covered by a when statement. I realized this
as soon as I saw [@antonarhipov's](https://github.com/antonarhipov) elegant solution.

Overall this was a great learning experience, which in the end, this is all about.

*For reference, my code vs Anton's*

```kotlin

input.getGroups().sumOf { getSecondAnswerWrong(it.split(newLine)) }

fun getSecondAnswerWrong(list: List<String>): Int = when {
        list.size == 1 -> list.single().length
        list.toSet().size == 1 -> 1
        else -> list.flatMap { it.toList() }
            .let { charList ->
                charList
                    .associateWith { c -> charList.count { it == c } }
                    .filterValues { it == list.size }
            }.size
    }
```

```kotlin
fun partTwoCorrect(input: String): Int =
        input.getGroups().map { it.split(newLine).map { s -> s.toSet() } }
            .sumOf { it.fold(it.first()) { a, b -> a intersect b }.count() }
```
### Day 7 notes
This may have been only my second time seeing a tree problem. I didn't recognize it and came up with an extremely 
inefficient solution (at least for part one).

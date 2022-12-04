fun solveDay04a(input: String): Int {
    val assignments = input.split("\n")
    return assignments.sumOf { assigment ->
        val (first, second) = assigment.split(",")
        val (firstStart, firstEnd) = first.split("-").map { it.toInt() }
        val (secondStart, secondEnd) = second.split("-").map { it.toInt() }
        val x = if ((firstEnd <= secondEnd && firstStart >= secondStart) || (firstEnd >= secondEnd && firstStart <= secondStart)) {
            1
        } else {
            0
        }
        x
    }
}

fun solveDay04b(input: String): Int {
    val assignments = input.split("\n")
    return assignments.sumOf { assigment ->
        val (first, second) = assigment.split(",")
        val (firstStart, firstEnd) = first.split("-").map { it.toInt() }
        val (secondStart, secondEnd) = second.split("-").map { it.toInt() }
        val x = if((firstStart..firstEnd).intersect(secondStart..secondEnd).isEmpty()) {
            0
        } else {
            1
        }
        x
    }
}

fun main() {
    val inputExample = readFile("day04_example.txt")
    val inputTask = readFile("day04.txt")

    println("Solution for task 1 example: ${solveDay04a(inputExample)}") // 2
    println("Solution for task 1 task:    ${solveDay04a(inputTask)}") // 567
    println("Solution for task 2 example: ${solveDay04b(inputExample)}") // 4
    println("Solution for task 2 task:    ${solveDay04b(inputTask)}") // 907
}


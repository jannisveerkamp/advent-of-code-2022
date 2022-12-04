fun solveDay03a(input: String): Int {
    val lines = input.split("\n")
    return lines.sumOf { line ->
        val first = line.substring(0, line.length / 2)
        val second = line.substring(line.length / 2)
        val common = first.toCharArray().toSet().intersect(second.toCharArray().toSet()).first()

        // Lowercase item types a through z have priorities 1 through 26.
        // Uppercase item types A through Z have priorities 27 through 52.
        val value = if (common.isUpperCase()) {
            common - 'A' + 27
        } else {
            common - 'a' + 1
        }
        value
    }
}

fun solveDay03b(input: String): Int {
    val lines = input.split("\n").chunked(3)
    return lines.sumOf { chunk ->
        val first = chunk[0]
        val second = chunk[1]
        val third = chunk[2]
        val common = first.first { char ->
            second.contains(char) && third.contains(char)
        }
        val value = if (common.isUpperCase()) {
            common - 'A' + 27
        } else {
            common - 'a' + 1
        }
        value
    }
}

fun main() {
    val inputExample = readFile("day03_example.txt")
    val inputTask = readFile("day03.txt")

    println("Solution for task 1 example: ${solveDay03a(inputExample)}") // 157
    println("Solution for task 1 task:    ${solveDay03a(inputTask)}") // 7821
    println("Solution for task 2 example: ${solveDay03b(inputExample)}") // 70
    println("Solution for task 2 task:    ${solveDay03b(inputTask)}") // 2752
}


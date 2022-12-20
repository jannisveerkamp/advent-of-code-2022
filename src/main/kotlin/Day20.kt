private data class Item(val number: Int, val originalPosition: Int)

fun solveDay20a(input: String): Int {
    val originalSequence = input.split("\n").map { it.toInt() }.mapIndexed { index, item -> Item(item, index) }
    val positions = originalSequence.toMutableList()
    val numberOfNumbers = positions.size

    originalSequence.forEach { number ->
        val index = positions.indexOf(number)
        positions.removeAt(index)
        positions.add((index + number.number).mod(positions.size), number)
    }

    val zero = positions.indexOfFirst { it.number == 0 }
    return listOf(1000, 2000, 3000).sumOf { positions[(zero + it) % numberOfNumbers].number }
}

fun main() {
    val inputExample = readFile("day20_example.txt")
    val inputTask = readFile("day20.txt")

    println("Solution for task 1 example: ${solveDay20a(inputExample)}") // 3
    println("Solution for task 1 task:    ${solveDay20a(inputTask)}") // 988
//    println("Solution for task 2 example: ${solveDay20a(inputExample)}") // ???
//    println("Solution for task 2 task:    ${solveDay20a(inputTask)}") // ???
}


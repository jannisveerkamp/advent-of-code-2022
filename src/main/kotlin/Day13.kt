import kotlinx.serialization.json.*

private val json = Json {
    isLenient = true
}

private sealed interface Element {
    data class ArrayElement(val children: List<Element>) : Element
    data class Primitive(val value: Int) : Element
}

private fun parsePackets(packet: JsonElement): Element = when (packet) {
    is JsonArray -> Element.ArrayElement(packet.map { parsePackets(it) })
    is JsonPrimitive -> Element.Primitive(packet.content.toInt())
    else -> error("unknown type: $packet")
}

private fun comparePackets(firstPacket: Element, secondPacket: Element): Int {
    return when {
        firstPacket is Element.Primitive && secondPacket is Element.Primitive ->
            secondPacket.value.compareTo(firstPacket.value)
        firstPacket is Element.ArrayElement && secondPacket is Element.ArrayElement -> {
            firstPacket.children.zip(secondPacket.children).forEach {
                val x = comparePackets(it.first, it.second)
                if (x != 0) {
                    return x
                }
            }
            if (firstPacket.children.size == secondPacket.children.size) {
                return 0
            } else if (firstPacket.children.size > secondPacket.children.size) {
                return -1
            } else {
                return 1
            }
        }
        firstPacket is Element.Primitive && secondPacket is Element.ArrayElement ->
            comparePackets(Element.ArrayElement(listOf(firstPacket)), secondPacket)
        firstPacket is Element.ArrayElement && secondPacket is Element.Primitive ->
            comparePackets(firstPacket, Element.ArrayElement(listOf(secondPacket)))
        else -> error("should never happen")
    }
}

fun solveDay13a(input: String): Int {
    val packetPairs = input.split("\n\n")
    return packetPairs.withIndex().sumOf { (index, packetPair) ->
        val (firstPacketRaw, secondPacketRaw) = packetPair.split("\n")
        val firstPacket = parsePackets(json.parseToJsonElement(firstPacketRaw))
        val secondPacket = parsePackets(json.parseToJsonElement(secondPacketRaw))
        if (comparePackets(firstPacket, secondPacket) != -1) {
            index + 1
        } else {
            0
        }
    }
}

fun solveDay13b(input: String): Int {
    val packetPairs = input.split("\n\n")
    val allPacketPairs = mutableListOf<Element>()

    packetPairs.forEach { packetPair ->
        val (firstPacketRaw, secondPacketRaw) = packetPair.split("\n")
        val firstPacket = parsePackets(json.parseToJsonElement(firstPacketRaw))
        val secondPacket = parsePackets(json.parseToJsonElement(secondPacketRaw))
        allPacketPairs.add(firstPacket)
        allPacketPairs.add(secondPacket)
    }

    val first = Element.ArrayElement(listOf(Element.ArrayElement(listOf(Element.Primitive(2)))))
    allPacketPairs.add(first)
    val second = Element.ArrayElement(listOf(Element.ArrayElement(listOf(Element.Primitive(6)))))
    allPacketPairs.add(second)

    allPacketPairs.sortWith(Comparator { o1: Element, o2: Element ->
        return@Comparator -comparePackets(o1, o2)
    })

    return (allPacketPairs.indexOf(first) + 1) * (allPacketPairs.indexOf(second) + 1)
}

fun main() {
    val inputExample = readFile("day13_example.txt")
    val inputTask = readFile("day13.txt")

    println("Solution for task 1 example: ${solveDay13a(inputExample)}") // 13
    println("Solution for task 1 task:    ${solveDay13a(inputTask)}")    // 5390
    println("Solution for task 2 example: ${solveDay13b(inputExample)}") // 140
    println("Solution for task 2 task:    ${solveDay13b(inputTask)}")    // 19261
}


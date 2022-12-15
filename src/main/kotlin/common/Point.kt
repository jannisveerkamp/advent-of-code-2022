package common

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun manhattan(other: Point): Int = abs(other.x - x) + abs(other.y - y)
}
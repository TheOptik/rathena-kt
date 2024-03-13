package de.theoptik.rathenakt.models

// [1][8][7]
// [2][0][6]
// [3][4][5]
enum class FacingDirection(val value: Int) {
    NEUTRAL(0),
    NORTH_WEST(1),
    NORTH(8),
    NORTH_EAST(7),
    WEST(2),
    EAST(6),
    SOUTH_WEST(3),
    SOUTH(4),
    SOUTH_EAST(5),
    ;

    override fun toString(): String {
        return value.toString()
    }
}

package de.theoptik.rathenakt.models

enum class TickType(val value: Int) {
    SERVER_TICKS(0),
    SECONDS_OF_DAY(1),
    EPOCH_TIME(2),
}

enum class CharInfoType(val value: Int) {
    CHARACTER_NAME(0),
    PARTY_NAME(1),
    GUILD_NAME(2),
    MAP_NAME(3),
}

data object Zeny: PermanentCharacterIntVariable("Zeny")


infix fun String.concat(statement: Statement): Statement {
    return ConcatenatedStatement(StringStatement(this), statement)
}

infix fun Statement.concat(string: String): Statement {
    return ConcatenatedStatement(this,StringStatement(string))
}

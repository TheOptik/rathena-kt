package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Variable<T>(
    val name: String,
) : Synthesizable {
    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)

    infix fun gt(other: Statement): Statement = GreaterThanStatement(VariableStatement(this), other)

    infix fun gt(other: Variable<*>): Statement = GreaterThanStatement(VariableStatement(this), VariableStatement(other))

    infix fun lt(other: Variable<*>): Statement = LesserThanStatement(VariableStatement(this), VariableStatement(other))

    infix fun lt(other: Statement): Statement = LesserThanStatement(VariableStatement(this), other)
}

class ScopeStringVariable(
    name: String,
) : Variable<String>(name)

class ScopeIntVariable(
    name: String,
) : Variable<Int>(name)

class CharacterStringVariable(
    name: String,
) : Variable<String>(name)

class CharacterIntVariable(
    name: String,
) : Variable<Int>(name)

open class PermanentCharacterIntVariable(
    name: String,
) : Variable<Int>(name)

class PermanentCharacterStringVariable(
    name: String,
) : Variable<String>(name)

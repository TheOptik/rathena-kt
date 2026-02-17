package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Variable<T>(
    val name: String,
) : Synthesizable {
    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)

    infix fun gt(other: Statement): Statement = GreaterThanStatement(VariableStatement(this), other)

    infix fun gt(other: Variable<*>): Statement = GreaterThanStatement(VariableStatement(this), VariableStatement(other))

    infix fun gt(other: Int): Statement = GreaterThanStatement(VariableStatement(this), IntLiteralStatement(other))

    infix fun lt(other: Variable<*>): Statement = LesserThanStatement(VariableStatement(this), VariableStatement(other))

    infix fun lt(other: Statement): Statement = LesserThanStatement(VariableStatement(this), other)

    infix fun lt(other: Int): Statement = LesserThanStatement(VariableStatement(this), IntLiteralStatement(other))

    infix fun gte(other: Statement): Statement = GreaterThanOrEqualStatement(VariableStatement(this), other)

    infix fun gte(other: Variable<*>): Statement = GreaterThanOrEqualStatement(VariableStatement(this), VariableStatement(other))

    infix fun gte(other: Int): Statement = GreaterThanOrEqualStatement(VariableStatement(this), IntLiteralStatement(other))

    infix fun lte(other: Statement): Statement = LesserThanOrEqualStatement(VariableStatement(this), other)

    infix fun lte(other: Variable<*>): Statement = LesserThanOrEqualStatement(VariableStatement(this), VariableStatement(other))

    infix fun lte(other: Int): Statement = LesserThanOrEqualStatement(VariableStatement(this), IntLiteralStatement(other))

    infix fun eq(other: Statement): Statement = EqualStatement(VariableStatement(this), other)

    infix fun eq(other: Variable<*>): Statement = EqualStatement(VariableStatement(this), VariableStatement(other))

    infix fun eq(other: Int): Statement = EqualStatement(VariableStatement(this), IntLiteralStatement(other))

    infix fun neq(other: Statement): Statement = NotEqualStatement(VariableStatement(this), other)

    infix fun neq(other: Variable<*>): Statement = NotEqualStatement(VariableStatement(this), VariableStatement(other))

    infix fun neq(other: Int): Statement = NotEqualStatement(VariableStatement(this), IntLiteralStatement(other))

    operator fun plus(other: Statement): Statement = AddStatement(VariableStatement(this), other)

    operator fun plus(other: Variable<*>): Statement = AddStatement(VariableStatement(this), VariableStatement(other))

    operator fun plus(other: Int): Statement = AddStatement(VariableStatement(this), IntLiteralStatement(other))

    operator fun minus(other: Statement): Statement = SubtractStatement(VariableStatement(this), other)

    operator fun minus(other: Variable<*>): Statement = SubtractStatement(VariableStatement(this), VariableStatement(other))

    operator fun minus(other: Int): Statement = SubtractStatement(VariableStatement(this), IntLiteralStatement(other))

    operator fun times(other: Statement): Statement = MultiplyStatement(VariableStatement(this), other)

    operator fun times(other: Variable<*>): Statement = MultiplyStatement(VariableStatement(this), VariableStatement(other))

    operator fun times(other: Int): Statement = MultiplyStatement(VariableStatement(this), IntLiteralStatement(other))

    operator fun div(other: Statement): Statement = DivideStatement(VariableStatement(this), other)

    operator fun div(other: Variable<*>): Statement = DivideStatement(VariableStatement(this), VariableStatement(other))

    operator fun div(other: Int): Statement = DivideStatement(VariableStatement(this), IntLiteralStatement(other))

    operator fun rem(other: Statement): Statement = ModuloStatement(VariableStatement(this), other)

    operator fun rem(other: Variable<*>): Statement = ModuloStatement(VariableStatement(this), VariableStatement(other))

    operator fun rem(other: Int): Statement = ModuloStatement(VariableStatement(this), IntLiteralStatement(other))
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

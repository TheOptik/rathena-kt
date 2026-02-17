package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Statement : Synthesizable {
    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)

    infix fun gt(other: Statement): Statement = GreaterThanStatement(this, other)

    infix fun gt(other: Variable<*>): Statement = GreaterThanStatement(this, VariableStatement(other))

    infix fun gt(other: Int): Statement = GreaterThanStatement(this, IntLiteralStatement(other))

    infix fun lt(other: Variable<*>): Statement = LesserThanStatement(this, VariableStatement(other))

    infix fun lt(other: Statement): Statement = LesserThanStatement(this, other)

    infix fun lt(other: Int): Statement = LesserThanStatement(this, IntLiteralStatement(other))

    infix fun gte(other: Statement): Statement = GreaterThanOrEqualStatement(this, other)

    infix fun gte(other: Variable<*>): Statement = GreaterThanOrEqualStatement(this, VariableStatement(other))

    infix fun gte(other: Int): Statement = GreaterThanOrEqualStatement(this, IntLiteralStatement(other))

    infix fun lte(other: Statement): Statement = LesserThanOrEqualStatement(this, other)

    infix fun lte(other: Variable<*>): Statement = LesserThanOrEqualStatement(this, VariableStatement(other))

    infix fun lte(other: Int): Statement = LesserThanOrEqualStatement(this, IntLiteralStatement(other))

    infix fun eq(other: Statement): Statement = EqualStatement(this, other)

    infix fun eq(other: Variable<*>): Statement = EqualStatement(this, VariableStatement(other))

    infix fun eq(other: Int): Statement = EqualStatement(this, IntLiteralStatement(other))

    infix fun neq(other: Statement): Statement = NotEqualStatement(this, other)

    infix fun neq(other: Variable<*>): Statement = NotEqualStatement(this, VariableStatement(other))

    infix fun neq(other: Int): Statement = NotEqualStatement(this, IntLiteralStatement(other))

    operator fun plus(other: Statement): Statement = AddStatement(this, other)

    operator fun plus(other: Variable<*>): Statement = AddStatement(this, VariableStatement(other))

    operator fun plus(other: Int): Statement = AddStatement(this, IntLiteralStatement(other))

    operator fun minus(other: Statement): Statement = SubtractStatement(this, other)

    operator fun minus(other: Variable<*>): Statement = SubtractStatement(this, VariableStatement(other))

    operator fun minus(other: Int): Statement = SubtractStatement(this, IntLiteralStatement(other))

    operator fun times(other: Statement): Statement = MultiplyStatement(this, other)

    operator fun times(other: Variable<*>): Statement = MultiplyStatement(this, VariableStatement(other))

    operator fun times(other: Int): Statement = MultiplyStatement(this, IntLiteralStatement(other))

    operator fun div(other: Statement): Statement = DivideStatement(this, other)

    operator fun div(other: Variable<*>): Statement = DivideStatement(this, VariableStatement(other))

    operator fun div(other: Int): Statement = DivideStatement(this, IntLiteralStatement(other))

    operator fun rem(other: Statement): Statement = ModuloStatement(this, other)

    operator fun rem(other: Variable<*>): Statement = ModuloStatement(this, VariableStatement(other))

    operator fun rem(other: Int): Statement = ModuloStatement(this, IntLiteralStatement(other))
}

data class ConcatenatedStatement(
    val first: Statement,
    val second: Statement,
) : Statement()

data class StringStatement(
    val value: String,
) : Statement()

data class VariableStatement(
    val variable: Variable<*>,
) : Statement()

data class GreaterThanStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class LesserThanStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class GreaterThanOrEqualStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class LesserThanOrEqualStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class EqualStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class NotEqualStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class AddStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class SubtractStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class MultiplyStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class DivideStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class ModuloStatement(
    val left: Statement,
    val right: Statement,
) : Statement()

data class IntLiteralStatement(
    val value: Int,
) : Statement()

data class TimeTickStatement(
    val type: TickType,
) : Statement()

data class CharInfoStatement(
    val type: CharInfoType,
) : Statement()

data class CallFuncStatement(
    val functionName: String,
    val argument: Statement,
) : Statement()

fun gettimetick(type: TickType): Statement = TimeTickStatement(type)

fun strcharinfo(type: CharInfoType): Statement = CharInfoStatement(type)

fun callfunc(
    functionName: String,
    argument: Statement,
): Statement = CallFuncStatement(functionName, argument)

fun callfunc(
    functionName: String,
    argument: Variable<*>,
): Statement = CallFuncStatement(functionName, VariableStatement(argument))

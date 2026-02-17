package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Statement : Synthesizable {
    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)
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

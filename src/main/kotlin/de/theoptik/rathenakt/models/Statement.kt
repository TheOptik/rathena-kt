package de.theoptik.rathenakt.models

sealed class Statement(private val value:String): Synthesizable {
    override fun synthesize(): String {
        return value
    }
}

data class VariableStatement(private val variable: Variable<*>): Statement(variable.synthesize())
data class GreaterThanStatement(private val left: Statement, private val right: Statement): Statement("${left.synthesize()} > ${right.synthesize()}")
private data  class TimeTickStatement(private val type:TickType):Statement("gettimetick(${type.value})")
private data class CharInfoStatement(private val type:CharInfoType):Statement("strcharinfo(${type.value})")
private data class CallFuncStatement(private val functionName:String, private val argument: Statement):Statement("callfunc(\"$functionName\", ${argument.synthesize()})")

fun gettimetick(type:TickType): Statement {
    return TimeTickStatement(type)
}

fun strcharinfo(type:CharInfoType): Statement {
    return CharInfoStatement(type)
}

fun callfunc(functionName:String, argument: Statement): Statement {
    return CallFuncStatement(functionName, argument)
}

fun callfunc(functionName:String, argument: Variable<*>): Statement {
    return CallFuncStatement(functionName, VariableStatement(argument))
}

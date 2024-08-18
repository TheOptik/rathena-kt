package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Statement: Synthesizable{
    override fun synthesize(synthesizer: Synthesizer):String {
        return synthesizer.synthesize(this)
    }
}

data class ConcatinazedStatement(val first:Statement, val second:Statement):Statement()
data class StringStatement(val value: String):Statement()
data class VariableStatement(val variable: Variable<*>): Statement()
data class GreaterThanStatement(val left: Statement, val right: Statement): Statement()
data  class TimeTickStatement( val type:TickType):Statement()
data class CharInfoStatement(val type:CharInfoType):Statement()
data class CallFuncStatement( val functionName:String, val argument: Statement):Statement()

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

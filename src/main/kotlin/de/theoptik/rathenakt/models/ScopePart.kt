package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class ScopePart : Synthesizable{
    open fun isTerminating():Boolean{return false}
    override fun synthesize(synthesizer: Synthesizer):String {
        return synthesizer.synthesize(this)
    }
}
data object ScopePartClose : ScopePart()

data class ScopePartMessage(val message: String) : ScopePart()

data class ScopePartChatMessage(val playerName:Statement,  val message: Statement) : ScopePart()

data class ScopePartSelect(val options:List<String>) : ScopePart()

data class ScopePartGoto(val scope: Scope) : ScopePart() {
    override fun isTerminating(): Boolean {
        return true
    }
}

data class ScopePartMenu(val options: Map<String, Scope?>) : ScopePart()

data object ScopePartEnd : ScopePart() {
    override fun isTerminating(): Boolean {
        return true
    }
}

data object ScopePartClear : ScopePart()

data class ScopePartVariableInstantiation<T>(val variable: Variable<T>, val initialValue: T) : ScopePart()

data class ScopePartStatement(val statement: Statement):ScopePart()

data class ScopePartIf(val condition: IfCondition) : ScopePart()

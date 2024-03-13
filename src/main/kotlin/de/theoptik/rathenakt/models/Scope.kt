package de.theoptik.rathenakt.models

import java.lang.System.lineSeparator

open class Scope(open val name: String? = null) : Synthesizable {
    protected val parts: MutableList<ScopePart> = mutableListOf()

    operator fun invoke(init: Scope.() -> Unit) {
        this.init()
    }

    fun clearMessages() {
        parts.add(ScopePartClear)
    }

    fun message(message: String) {
        parts.add(ScopePartMessage(message))
    }

    fun chatMessage(playerName:String, message: String) {
        parts.add(ScopePartChatMessage(playerName, message))
    }

    fun chatMessage(playerName:Statement, message: String) {
        parts.add(ScopePartChatMessage(playerName.synthesize(), message))
    }

    fun goto(scope: Scope) {
        parts.add(ScopePartGoto(scope))
    }

    fun menu(init: Menu.() -> Unit) {
        val menu = Menu()
        menu.init()
        parts.add(ScopePartMenu(menu.options))
    }

    fun end() {
        parts.add(ScopePartEnd)
    }

    override fun synthesize(): String {
        val partsWithEnd =
            if (parts.lastOrNull()?.isTerminating() == true) {
                parts + ScopePartClose
            } else {
                parts
            }
        return partsWithEnd.map { "\t${it.synthesize()}" }.joinToString(lineSeparator())
    }

    fun variable(
        name: String,
        initialValue: String,
    ): Variable<String> {
        val variable = ScopeStringVariable(name)
        parts.add(ScopePartVariableInstantiation(variable, initialValue))
        return variable
    }

    fun variable(
        name: String,
        initialValue: Int,
    ): Variable<Int> {
        val variable = ScopeIntVariable(name)
        parts.add(ScopePartVariableInstantiation(variable, initialValue))
        return variable
    }

    fun characterVariable(name: String, _typeHint: Int.Companion):Variable<Int> {
        return CharacterIntVariable(name)
    }

    fun characterVariable(name: String, _typeHint: String.Companion):Variable<String> {
        return CharacterStringVariable(name)
    }

    fun characterVariable(
        name: String,
        initialValue: String,
    ): Variable<String> {
        val variable = CharacterStringVariable(name)
        parts.add(ScopePartVariableInstantiation(variable, initialValue))
        return variable
    }

    fun characterVariable(
        name: String,
        initialValue: Int,
    ): Variable<Int> {
        val variable = CharacterIntVariable(name)
        parts.add(ScopePartVariableInstantiation(variable, initialValue))
        return variable
    }

    fun `if`(statement:Statement, init: IfCondition.() -> Unit)  {
        val ifScope = IfCondition(statement)
        ifScope.init()
        parts.add(ScopePartIf(ifScope))
    }

    fun `if`(variable:Variable<*>, init: IfCondition.() -> Unit)  {
        this.`if`(VariableStatement(variable),init)
    }
}

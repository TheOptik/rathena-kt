package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

sealed class Scope(
    open val name: String? = null,
) : Synthesizable {
    val parts: MutableList<ScopePart> = mutableListOf()

    operator fun invoke(init: Scope.() -> Unit) {
        this.init()
    }

    fun clearMessages() {
        parts.add(ScopePartClear)
    }

    fun message(message: String) {
        parts.add(ScopePartMessage(message))
    }

    fun chatMessage(
        playerName: String,
        message: String,
    ) {
        chatMessage(StringStatement(playerName), message)
    }

    fun chatMessage(
        playerName: Statement,
        message: String,
    ) {
        chatMessage(playerName, StringStatement(message))
    }

    fun chatMessage(
        playerName: Statement,
        message: Statement,
    ) {
        parts.add(ScopePartChatMessage(playerName, message))
    }

    fun select(options: String) {
        parts.add(ScopePartSelect(options.split(":")))
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

    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)

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

    fun characterVariable(
        name: String,
        _typeHint: Int.Companion,
    ): Variable<Int> = CharacterIntVariable(name)

    fun characterVariable(
        name: String,
        _typeHint: String.Companion,
    ): Variable<String> = CharacterStringVariable(name)

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

    fun `if`(
        statement: Statement,
        init: IfCondition.() -> Unit,
    ) {
        val ifScope = IfCondition(statement)
        ifScope.init()
        parts.add(ScopePartIf(ifScope))
    }

    fun `if`(
        variable: Variable<*>,
        init: IfCondition.() -> Unit,
    ) {
        this.`if`(VariableStatement(variable), init)
    }

    fun assign(
        variable: Variable<*>,
        value: Statement,
    ) {
        parts.add(ScopePartAssignment(variable, value))
    }

    fun assign(
        variable: Variable<*>,
        value: Int,
    ) {
        parts.add(ScopePartAssignment(variable, IntLiteralStatement(value)))
    }

    fun assign(
        variable: Variable<*>,
        value: Variable<*>,
    ) {
        parts.add(ScopePartAssignment(variable, VariableStatement(value)))
    }

    fun plusAssign(
        variable: Variable<*>,
        value: Statement,
    ) {
        parts.add(ScopePartCompoundAssignment(variable, "+=", value))
    }

    fun plusAssign(
        variable: Variable<*>,
        value: Int,
    ) {
        parts.add(ScopePartCompoundAssignment(variable, "+=", IntLiteralStatement(value)))
    }

    fun minusAssign(
        variable: Variable<*>,
        value: Statement,
    ) {
        parts.add(ScopePartCompoundAssignment(variable, "-=", value))
    }

    fun minusAssign(
        variable: Variable<*>,
        value: Int,
    ) {
        parts.add(ScopePartCompoundAssignment(variable, "-=", IntLiteralStatement(value)))
    }

    fun minusAssign(
        variable: Variable<*>,
        value: Variable<*>,
    ) {
        parts.add(ScopePartCompoundAssignment(variable, "-=", VariableStatement(value)))
    }
}

class SimpleScope(
    name: String?,
) : Scope(name)

class Npc(
    name: String?,
    val sprite: Int?,
    val mapReferences: MapReferences?,
    val coordinates: Coordinates?,
) : Scope(name) {
    val scopes: MutableMap<String, Scope> = mutableMapOf()

    fun scope(
        scopeName: String,
        init: Scope.() -> Unit,
    ): Scope {
        val scope = SimpleScope(scopeName)
        scope.init()
        scopes[scopeName] = scope
        return scope
    }

    fun scope(scopeName: String): Scope {
        val scope = SimpleScope(scopeName)
        scopes[scopeName] = scope
        return scope
    }

    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)
}

class IfCondition(
    val statement: Statement,
) : Scope() {
    override fun synthesize(synthesizer: Synthesizer): String = synthesizer.synthesize(this)
}

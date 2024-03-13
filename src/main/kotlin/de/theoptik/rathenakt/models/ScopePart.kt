package de.theoptik.rathenakt.models

sealed class ScopePart : Synthesizable{
    open fun isTerminating():Boolean{return false}
}

data object ScopePartClose : ScopePart() {
    override fun synthesize(): String {
        return "close;"
    }
}

data class ScopePartMessage(private val message: String) : ScopePart() {
    override fun synthesize(): String {
        return "mes \"${this.message}\";"
    }
}

data class ScopePartChatMessage(private val playerName:String, private val message: String) : ScopePart() {
    override fun synthesize(): String {
        return "message $playerName, $message;"
    }
}

data class ScopePartGoto(private val scope: Scope) : ScopePart() {
    override fun isTerminating(): Boolean {
        return true;
    }
    override fun synthesize(): String {
        return "goto ${this.scope.name ?: "-"};"
    }
}

data class ScopePartMenu(private val options: Map<String, Scope?>) : ScopePart() {
    override fun synthesize(): String {
        return "menu ${
            options
                .map { "\"${it.key}\",${it.value?.name ?: "-"}" }
                .joinToString(",")
        };"
    }
}

data object ScopePartEnd : ScopePart() {
    override fun synthesize(): String {
        return "end;"
    }
}

data object ScopePartClear : ScopePart() {
    override fun synthesize(): String {
        return "clear;"
    }
}

data class ScopePartVariableInstantiation<T>(private val variable: Variable<T>, private val initialValue: T) : ScopePart() {
    override fun synthesize(): String {
        return "${variable.synthesize()} = $initialValue;"
    }
}

data class ScopePartStatement(private val statement: Statement):ScopePart(){
    override fun synthesize(): String {
        return statement.synthesize()
    }

}

data class ScopePartIf(private val condition: IfCondition) : ScopePart() {
    override fun synthesize(): String {
        return condition.synthesize()
    }
}

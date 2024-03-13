package de.theoptik.rathenakt.models

sealed class ScopePart : Synthesizable

data object ScopePartClose : ScopePart() {
    override fun synthesize(): String {
        return "close";
    }
}

data class ScopePartMessage(private val message: String) : ScopePart() {
    override fun synthesize(): String {
        return "mes \"${this.message}\"";
    }
}

data class ScopePartGoto(private val scope: Scope) : ScopePart() {
    override fun synthesize(): String {
        return "goto ${this.scope.name ?: "-"}";
    }
}

data class ScopePartMenu(private val options: Map<String, Scope?>) : ScopePart() {
    override fun synthesize(): String {
        return "menu ${
            options
                .map { "\"${it.key}\",${it.value?.name ?: "-"}" }
                .joinToString(",")
        }";
    }
}

data object ScopePartClear : ScopePart() {
    override fun synthesize(): String {
        return "clear";
    }
}

data class ScopePartVaraibleInstantiation<T>(private val variable:Variable<T>):ScopePart(){
    override fun synthesize(): String {
        return variable.synthesize();
    }
}
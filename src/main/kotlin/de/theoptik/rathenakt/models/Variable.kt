package de.theoptik.rathenakt.models

private interface Prefixable {
    fun synthesizeVariablePrefix(): String
}

private data object TemporaryCharacterVariable : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return "@"
    }
}

private data object ScopeVariablePrefix : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return ".@"
    }
}

private interface Postfixable {
    fun synthesizeVariablePostfix(): String
}

private data object StringVariablePostfix : Postfixable {
    override fun synthesizeVariablePostfix(): String {
        return "$"
    }
}

private data object IntVariablePostfix : Postfixable {
    override fun synthesizeVariablePostfix(): String {
        return ""
    }
}

sealed class Variable<T>(private val name: String, private val initialValue: T) : Synthesizable, Prefixable,
    Postfixable {
    protected open fun synthesizeVariableName(): String {
        return synthesizeVariablePrefix() + name + synthesizeVariablePostfix()
    }

    protected open fun synthesizeDeclaration(): String {
        return " = $initialValue"
    }

    override fun synthesize(): String {
        return synthesizeVariableName() + synthesizeDeclaration()
    }
}


class TemporaryCharacterStringVariable(name: String, initialValue: String) : Variable<String>(name, initialValue),
    Prefixable by TemporaryCharacterVariable, Postfixable by StringVariablePostfix

class ScopeStringVariable(name: String, initialValue: String) : Variable<String>(name, initialValue),
    Prefixable by ScopeVariablePrefix, Postfixable by StringVariablePostfix

class ScopeIntVariable(name: String, initialValue: Int) : Variable<Int>(name, initialValue),
    Prefixable by ScopeVariablePrefix, Postfixable by IntVariablePostfix
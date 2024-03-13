package de.theoptik.rathenakt.models

interface Prefixable {
    fun synthesizeVariablePrefix(): String {
        return ""
    }
}

class TemporaryCharacterVariable : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return "@"
    }
}

class ScopeVariable: Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return ".@"
    }
}

interface Postfixable {
    fun synthesizeVariablePostfix(): String {
        return ""
    }
}

class StringVariable : Postfixable {
    override fun synthesizeVariablePostfix(): String {
        return "$"
    }
}

class IntVariable : Postfixable

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
    Prefixable by TemporaryCharacterVariable(), Postfixable by StringVariable()

class ScopeStringVariable(name:String, initialValue: String): Variable<String>(name,initialValue),
        Prefixable by ScopeVariable(), Postfixable by StringVariable()

class ScopeIntVariable(name:String, initialValue: Int): Variable<Int>(name,initialValue),
    Prefixable by ScopeVariable(), Postfixable by IntVariable()
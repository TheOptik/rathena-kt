package de.theoptik.rathenakt.models

interface Prefixable {
    fun synthesizeVariablePrefix(): String
}

data object TemporaryCharacterVariable : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return "@"
    }
}

data object ScopeVariablePrefix : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return ".@"
    }
}

data object CharacterVariablePrefix : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return "@"
    }
}

data object PermanentCharacterVariablePrefix : Prefixable {
    override fun synthesizeVariablePrefix(): String {
        return ""
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

sealed class Variable<T>(private val name: String) :
    Synthesizable,
    Prefixable,
    Postfixable {
    override fun synthesize(): String {
        return synthesizeVariablePrefix() + name + synthesizeVariablePostfix()
    }

    infix fun gt(other: Statement):Statement {
        return GreaterThanStatement(VariableStatement(this), other)
    }

    infix fun gt(other: Variable<*>):Statement {
        return GreaterThanStatement(VariableStatement(this), VariableStatement(other))
    }

    infix fun lt(other: Variable<*>): Statement {
        return GreaterThanStatement(VariableStatement(this), VariableStatement(other))
    }

    infix fun lt(other: Statement): Statement {
        return GreaterThanStatement(VariableStatement(this), other)
    }
}

class TemporaryCharacterStringVariable(name: String) :
    Variable<String>(name),
    Prefixable by TemporaryCharacterVariable,
    Postfixable by StringVariablePostfix

class ScopeStringVariable(name: String) :
    Variable<String>(name),
    Prefixable by ScopeVariablePrefix,
    Postfixable by StringVariablePostfix

class ScopeIntVariable(name: String) :
    Variable<Int>(name),
    Prefixable by ScopeVariablePrefix,
    Postfixable by IntVariablePostfix

class CharacterStringVariable(name: String) :
    Variable<String>(name),
    Prefixable by CharacterVariablePrefix,
    Postfixable by StringVariablePostfix

open class CharacterIntVariable(name: String) :
    Variable<Int>(name),
    Prefixable by CharacterVariablePrefix,
    Postfixable by IntVariablePostfix

open class PermanentCharacterIntVariable(name: String) :
    Variable<Int>(name),
    Prefixable by PermanentCharacterVariablePrefix,
    Postfixable by IntVariablePostfix

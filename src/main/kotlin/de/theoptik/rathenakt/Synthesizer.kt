package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.*
import java.lang.System.lineSeparator

class Synthesizer {

    fun synthesize(part: ScopePart): String {
        return when (part) {
            is ScopePartChatMessage -> "message ${part.playerName.synthesize(this)}, ${part.message.synthesize(this)};"
            is ScopePartClear -> "clear;"
            is ScopePartClose -> "close;"
            is ScopePartEnd -> "end;"
            is ScopePartGoto -> "goto ${part.scope.name ?: "-"};"
            is ScopePartIf -> part.condition.synthesize(this)
            is ScopePartMenu -> "menu ${
                part.options
                    .map { "\"${it.key}\",${it.value?.name ?: "-"}" }
                    .joinToString(",")
            };"

            is ScopePartMessage -> "mes \"${part.message}\";"
            is ScopePartStatement -> part.statement.synthesize(this)
            is ScopePartVariableInstantiation<*> -> "${part.variable.synthesize(this)} = ${part.initialValue};"
            is ScopePartSelect -> part.options.joinToString(":")
        }
    }

    fun synthesize(statement: Statement): String {
        return when (statement) {
            is CallFuncStatement -> "callfunc(\"${statement.functionName}\", ${statement.argument.synthesize(this)})"
            is CharInfoStatement -> "strcharinfo(${statement.type.value})"
            is GreaterThanStatement -> "${statement.left.synthesize(this)} > ${statement.right.synthesize(this)}"
            is TimeTickStatement -> "gettimetick(${statement.type.value})"
            is VariableStatement -> statement.variable.synthesize(this)
            is StringStatement -> statement.value
            is ConcatinazedStatement -> "${statement.first.synthesize(this)}${statement.second.synthesize(this)}"
        }
    }

    fun synthesize(scope: Scope): String {
        return when (scope) {
            is IfCondition -> listOf("if(${scope.statement.synthesize(this)}){", serialize(scope), "}").joinToString(
                System.lineSeparator()
            )

            is Npc -> listOf(
                "${
                    synthesizePosition(
                        scope.mapReferences,
                        scope.coordinates
                    )
                }\tscript\t${scope.name ?: ""}\t${scope.sprite ?: "-1"},{",
                serialize(scope),
                scope.scopes.map { "${it.key}:\n${it.value.synthesize(this)}\n" }.joinToString(""),
                "}",
            ).joinToString(lineSeparator())

            else -> serialize(scope)
        }
    }

    private fun serialize(scope: Scope): String {
        val partsWithEnd =
            if (scope.parts.lastOrNull()?.isTerminating() == true) {
                scope.parts + ScopePartClose
            } else {
                scope.parts
            }
        return partsWithEnd.map { "\t${it.synthesize(this)}" }.joinToString(lineSeparator())
    }

    fun synthesize(variable: Variable<*>): String {
        return when (variable) {
            is PermanentCharacterIntVariable -> variable.name
            is PermanentCharacterStringVariable -> "${variable.name}#"
            is CharacterStringVariable -> "@${variable.name}$"
            is CharacterIntVariable -> "@${variable.name}"
            is ScopeStringVariable -> ".@${variable.name}$"
            is ScopeIntVariable -> ".@${variable.name}"
        }
    }
}

private fun synthesizePosition(
    mapReferences: MapReferences?,
    coordinates: Coordinates?,
): String {
    if (mapReferences === null || coordinates === null) {
        return "-"
    }
    return "$mapReferences,${coordinates.x},${coordinates.y},${coordinates.facingDirection}"
}
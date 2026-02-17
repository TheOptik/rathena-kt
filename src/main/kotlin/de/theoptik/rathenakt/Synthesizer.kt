package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.CallFuncStatement
import de.theoptik.rathenakt.models.CharInfoStatement
import de.theoptik.rathenakt.models.CharacterIntVariable
import de.theoptik.rathenakt.models.CharacterStringVariable
import de.theoptik.rathenakt.models.ConcatenatedStatement
import de.theoptik.rathenakt.models.Coordinates
import de.theoptik.rathenakt.models.GreaterThanStatement
import de.theoptik.rathenakt.models.IfCondition
import de.theoptik.rathenakt.models.AddStatement
import de.theoptik.rathenakt.models.DivideStatement
import de.theoptik.rathenakt.models.EqualStatement
import de.theoptik.rathenakt.models.GreaterThanOrEqualStatement
import de.theoptik.rathenakt.models.IntLiteralStatement
import de.theoptik.rathenakt.models.LesserThanOrEqualStatement
import de.theoptik.rathenakt.models.LesserThanStatement
import de.theoptik.rathenakt.models.ModuloStatement
import de.theoptik.rathenakt.models.MultiplyStatement
import de.theoptik.rathenakt.models.NotEqualStatement
import de.theoptik.rathenakt.models.MapReferences
import de.theoptik.rathenakt.models.Npc
import de.theoptik.rathenakt.models.PermanentCharacterIntVariable
import de.theoptik.rathenakt.models.PermanentCharacterStringVariable
import de.theoptik.rathenakt.models.Scope
import de.theoptik.rathenakt.models.ScopeIntVariable
import de.theoptik.rathenakt.models.ScopePart
import de.theoptik.rathenakt.models.ScopePartChatMessage
import de.theoptik.rathenakt.models.ScopePartClear
import de.theoptik.rathenakt.models.ScopePartClose
import de.theoptik.rathenakt.models.ScopePartEnd
import de.theoptik.rathenakt.models.ScopePartGoto
import de.theoptik.rathenakt.models.ScopePartIf
import de.theoptik.rathenakt.models.ScopePartMenu
import de.theoptik.rathenakt.models.ScopePartMessage
import de.theoptik.rathenakt.models.ScopePartAssignment
import de.theoptik.rathenakt.models.ScopePartCompoundAssignment
import de.theoptik.rathenakt.models.ScopePartSelect
import de.theoptik.rathenakt.models.ScopePartStatement
import de.theoptik.rathenakt.models.SubtractStatement
import de.theoptik.rathenakt.models.ScopePartVariableInstantiation
import de.theoptik.rathenakt.models.ScopeStringVariable
import de.theoptik.rathenakt.models.Statement
import de.theoptik.rathenakt.models.StringStatement
import de.theoptik.rathenakt.models.TimeTickStatement
import de.theoptik.rathenakt.models.Variable
import de.theoptik.rathenakt.models.VariableStatement
import java.lang.System.lineSeparator

class Synthesizer {
    fun synthesize(part: ScopePart): String =
        when (part) {
            is ScopePartChatMessage -> {
                "message ${part.playerName.synthesize(this)}, ${part.message.synthesize(this)};"
            }

            is ScopePartClear -> {
                "clear;"
            }

            is ScopePartClose -> {
                "close;"
            }

            is ScopePartEnd -> {
                "end;"
            }

            is ScopePartGoto -> {
                "goto ${part.scope.name ?: "-"};"
            }

            is ScopePartIf -> {
                part.condition.synthesize(this)
            }

            is ScopePartMenu -> {
                "menu ${
                    part.options
                        .map { "\"${it.key}\",${it.value?.name ?: "-"}" }
                        .joinToString(",")
                };"
            }

            is ScopePartMessage -> {
                "mes \"${part.message}\";"
            }

            is ScopePartStatement -> {
                part.statement.synthesize(this)
            }

            is ScopePartVariableInstantiation<*> -> {
                "${part.variable.synthesize(this)} = ${part.initialValue};"
            }

            is ScopePartSelect -> {
                part.options.joinToString(":")
            }

            is ScopePartAssignment -> {
                "${part.variable.synthesize(this)} = ${part.value.synthesize(this)};"
            }

            is ScopePartCompoundAssignment -> {
                "${part.variable.synthesize(this)} ${part.operator} ${part.value.synthesize(this)};"
            }
        }

    fun synthesize(statement: Statement): String =
        when (statement) {
            is CallFuncStatement -> "callfunc(\"${statement.functionName}\", ${statement.argument.synthesize(this)})"
            is CharInfoStatement -> "strcharinfo(${statement.type.value})"
            is GreaterThanStatement -> "${statement.left.synthesize(this)} > ${statement.right.synthesize(this)}"
            is LesserThanStatement -> "${statement.left.synthesize(this)} < ${statement.right.synthesize(this)}"
            is TimeTickStatement -> "gettimetick(${statement.type.value})"
            is VariableStatement -> statement.variable.synthesize(this)
            is StringStatement -> statement.value
            is ConcatenatedStatement -> "${statement.first.synthesize(this)}${statement.second.synthesize(this)}"
            is GreaterThanOrEqualStatement -> "${statement.left.synthesize(this)} >= ${statement.right.synthesize(this)}"
            is LesserThanOrEqualStatement -> "${statement.left.synthesize(this)} <= ${statement.right.synthesize(this)}"
            is EqualStatement -> "${statement.left.synthesize(this)} == ${statement.right.synthesize(this)}"
            is NotEqualStatement -> "${statement.left.synthesize(this)} != ${statement.right.synthesize(this)}"
            is AddStatement -> "${statement.left.synthesize(this)} + ${statement.right.synthesize(this)}"
            is SubtractStatement -> "${statement.left.synthesize(this)} - ${statement.right.synthesize(this)}"
            is MultiplyStatement -> "${statement.left.synthesize(this)} * ${statement.right.synthesize(this)}"
            is DivideStatement -> "${statement.left.synthesize(this)} / ${statement.right.synthesize(this)}"
            is ModuloStatement -> "${statement.left.synthesize(this)} % ${statement.right.synthesize(this)}"
            is IntLiteralStatement -> statement.value.toString()
        }

    fun synthesize(scope: Scope): String =
        when (scope) {
            is IfCondition -> {
                listOf("if(${scope.statement.synthesize(this)}){", serialize(scope), "}").joinToString(
                    System.lineSeparator(),
                )
            }

            is Npc -> {
                listOf(
                    "${
                        synthesizePosition(
                            scope.mapReferences,
                            scope.coordinates,
                        )
                    }\tscript\t${scope.name ?: ""}\t${scope.sprite ?: "-1"},{",
                    serialize(scope),
                    scope.scopes.map { "${it.key}:\n${it.value.synthesize(this)}\n" }.joinToString(""),
                    "}",
                ).joinToString(lineSeparator())
            }

            else -> {
                serialize(scope)
            }
        }

    private fun serialize(scope: Scope): String =
        if (scope.parts.isEmpty() || scope.parts.last().isTerminating()) {
            scope.parts
        } else {
            scope.parts + ScopePartClose
        }.joinToString(lineSeparator()) { "\t${it.synthesize(this)}" }

    fun synthesize(variable: Variable<*>): String =
        when (variable) {
            is PermanentCharacterIntVariable -> variable.name
            is PermanentCharacterStringVariable -> "${variable.name}#"
            is CharacterStringVariable -> "@${variable.name}$"
            is CharacterIntVariable -> "@${variable.name}"
            is ScopeStringVariable -> ".@${variable.name}$"
            is ScopeIntVariable -> ".@${variable.name}"
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

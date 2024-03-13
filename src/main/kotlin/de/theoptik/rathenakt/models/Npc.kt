package de.theoptik.rathenakt.models

import java.lang.System.lineSeparator


class Npc(
    override val name: String?,
    private val sprite: Int?,
    private val mapReferences: MapReferences?,
    private val coordinates: Coordinates?
): Scope(name) {
    private val scopes:MutableMap<String, Scope> = mutableMapOf();
    fun scope(scopeName: String, init: Scope.() -> Unit): Scope {
        val scope = Scope(scopeName)
        scope.init()
        scopes[scopeName] = scope
        return scope;
    }
    fun scope(scopeName: String): Scope {
        val scope = Scope(scopeName)
        scopes[scopeName] = scope
        return scope
    }
    override fun synthesize():String{
        return listOf("${synthesizePosition(this.mapReferences,this.coordinates)}\tscript\t${this.name ?: ""}\t${this.sprite ?: "-1"},{",
            super.synthesize(),
                this.scopes.map{"${it.key}:\n${it.value.synthesize()}\n"}.joinToString(""),
                "}").joinToString(lineSeparator())
    }
}

fun npc(name: String, sprite: Int = -1, mapReferences: MapReferences? = null, coordinates: Coordinates? = null, init: Npc.() -> Unit): Npc {
    val npc = Npc(name, sprite, mapReferences, coordinates)
    npc.init()
    return npc;
}

private fun synthesizePosition(mapReferences: MapReferences?, coordinates: Coordinates?): String {
    if (mapReferences === null || coordinates === null) {
        return "-";
    }

    return "${mapReferences},${coordinates.x},${coordinates.y},${coordinates.facingDirection}";
}

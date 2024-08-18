package de.theoptik.rathenakt.models

import java.lang.System.lineSeparator



fun npc(
    name: String,
    sprite: Int = -1,
    mapReferences: MapReferences? = null,
    coordinates: Coordinates? = null,
    init: Npc.() -> Unit,
): Npc {
    val npc = Npc(name, sprite, mapReferences, coordinates)
    npc.init()
    return npc
}



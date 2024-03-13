package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.Coordinates
import de.theoptik.rathenakt.models.FacingDirection
import de.theoptik.rathenakt.models.MapReferences
import de.theoptik.rathenakt.models.npc

fun main() {
    val testNpc =
        npc(
            name = "Test",
            sprite = 589,
            mapReferences = MapReferences.PRONTERA,
            coordinates = Coordinates(x = 156, y = 145, facingDirection = FacingDirection.SOUTH),
        ) {
            val hello = scope("L_Hello")

            val goodbye =
                scope("L_Goodbye") {
                    clearMessages()
                    addMessage("Have a nice day!")
                }

            hello {
                clearMessages()
                addMessage("Hello traveler")
                goto(goodbye)
            }

            addMessage("What would you like to say to me?")

            menu {
                option("hello", hello)
                option("goodbye", goodbye)
                option("screw you", null)
            }
        }
    println(testNpc.synthesize())

    // rathena-kt version of https://github.com/rathena/rathena/blob/master/npc/custom/healer.txt
    val healer =
        npc(name = "Healer") {
            val healDelay = characterVariable("HD")

            val price = variable("Price", 0) // Zeny required for heal
            val buffs = variable("Buffs", 0) // Also buff players? (1: yes / 0: no)
            val delay = variable("Delay", 0) // Heal delay, in seconds

            `if` {}
        }

    println(healer.synthesize())
}

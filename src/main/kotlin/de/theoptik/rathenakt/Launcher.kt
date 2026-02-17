package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.CharInfoType
import de.theoptik.rathenakt.models.Coordinates
import de.theoptik.rathenakt.models.FacingDirection
import de.theoptik.rathenakt.models.MapReferences
import de.theoptik.rathenakt.models.TickType.EPOCH_TIME
import de.theoptik.rathenakt.models.Zeny
import de.theoptik.rathenakt.models.callfunc
import de.theoptik.rathenakt.models.concat
import de.theoptik.rathenakt.models.gettimetick
import de.theoptik.rathenakt.models.npc
import de.theoptik.rathenakt.models.strcharinfo

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
                    message("Have a nice day!")
                }

            hello {
                clearMessages()
                message("Hello traveler")
                goto(goodbye)
            }

            message("What would you like to say to me?")

            menu {
                option("hello", hello)
                option("goodbye", goodbye)
                option("screw you", null)
            }
        }
    val synthesizer = Synthesizer()
    println(testNpc.synthesize(synthesizer))

    // rathena-kt version of https://github.com/rathena/rathena/blob/master/npc/custom/healer.txt
    val healer =
        npc(name = "Healer") {
            val healDelay = characterVariable("HD", Int)

            val price = variable("Price", 0) // Zeny required for heal
            val buffs = variable("Buffs", 0) // Also buff players? (1: yes / 0: no)
            val delay = variable("Delay", 0) // Heal delay, in seconds

            `if`(healDelay gt gettimetick(EPOCH_TIME)) {
                end()
            }
            `if`(price) {
                chatMessage(
                    strcharinfo(CharInfoType.CHARACTER_NAME),
                    "Healing costs " concat callfunc("F_InsertComma", price) concat " Zeny.",
                )
            }
            `if`(Zeny lt price) {
                end()
            }
//            `if`(select("^0055FFHeal^000000:^777777Cancel^000000") == 2) {
//                end()
//                Zeny -= price;
//            }
//            specialeffect2 EF_HEAL2;
//            percentheal 100,100;
//            if (buffs) {
//            specialeffect2 EF_INCAGILITY;
//            sc_start SC_INCREASEAGI,240000,10;
//            specialeffect2 EF_BLESSING;
//            sc_start SC_BLESSING,240000,10;
//        }
            `if`(delay) {
//                healDelay = gettimetick(EPOCH_TIME) + delay;
                end()
            }
        }
    val synthesizer2 = Synthesizer()
    println(healer.synthesize(synthesizer2))
}

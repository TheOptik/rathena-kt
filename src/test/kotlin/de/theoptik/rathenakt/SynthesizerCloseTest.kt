package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse

class SynthesizerCloseTest {

    private val synthesizer = Synthesizer()

    @Test
    fun `scope ending with message should have close appended`() {
        val npc = npc(name = "CloseTest", sprite = 100) {
            message("Hello!")
        }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "close;", message = "A scope ending with a message should have 'close;' appended")
    }

    @Test
    fun `scope ending with goto should not have close appended`() {
        val npc = npc(name = "GotoTest", sprite = 100) {
            val target = scope("L_Target") {
                message("Arrived!")
            }
            goto(target)
        }
        val output = npc.synthesize(synthesizer)
        // The main scope ends with goto — should NOT have close after it
        val mainBody = output.substringAfter("{").substringBefore("L_Target:")
        assertFalse(mainBody.contains("close;"), "A scope ending with goto should not have 'close;' appended")
    }

    @Test
    fun `scope ending with end should not have close appended`() {
        val npc = npc(name = "EndTest", sprite = 100) {
            message("Goodbye!")
            end()
        }
        val output = npc.synthesize(synthesizer)
        // end; should be the last statement, no close; after it
        val lines = output.lines().map { it.trim() }.filter { it.isNotEmpty() }
        val endIndex = lines.indexOfLast { it == "end;" }
        val closeIndex = lines.indexOfLast { it == "close;" }
        assertFalse(
            closeIndex > endIndex,
            "A scope ending with end; should not have close; appended after it. Output:\n$output"
        )
    }

    @Test
    fun `scope ending with menu should have close appended`() {
        val npc = npc(name = "MenuTest", sprite = 100) {
            message("Choose:")
            menu {
                option("Option A", null)
                option("Option B", null)
            }
        }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "close;", message = "A scope ending with a menu should have 'close;' appended")
    }

    @Test
    fun `empty scope should not have close appended`() {
        val npc = npc(name = "EmptyTest", sprite = 100) {}
        val output = npc.synthesize(synthesizer)
        assertFalse(output.contains("close;"), "An empty scope should not have 'close;' appended")
    }

    @Test
    fun `sub-scope ending with message should have close appended`() {
        val npc = npc(name = "SubScopeTest", sprite = 100) {
            val label = scope("L_Hello") {
                message("Hi there!")
            }
            goto(label)
        }
        val output = npc.synthesize(synthesizer)
        // The L_Hello scope ends with a message, so it should get close;
        val labelSection = output.substringAfter("L_Hello:")
        assertContains(labelSection, "close;", message = "A sub-scope ending with a message should have 'close;' appended")
    }
}

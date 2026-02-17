package de.theoptik.rathenakt

import de.theoptik.rathenakt.models.Zeny
import de.theoptik.rathenakt.models.npc
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class ArithmeticTest {
    private val synthesizer = Synthesizer()

    @Test
    fun `variable plus int should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val price = variable("Price", 0)
                assign(price, price + 10)
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, ".@Price = .@Price + 10;")
    }

    @Test
    fun `variable minus variable should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val price = variable("Price", 100)
                minusAssign(Zeny, price)
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "Zeny -= .@Price;")
    }

    @Test
    fun `compound plus assign should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val counter = variable("Counter", 0)
                plusAssign(counter, 1)
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, ".@Counter += 1;")
    }

    @Test
    fun `equality comparison should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val choice = variable("Choice", 0)
                `if`(choice eq 2) {
                    end()
                }
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "if(.@Choice == 2)")
    }

    @Test
    fun `not equal comparison should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val status = variable("Status", 0)
                `if`(status neq 0) {
                    message("Active!")
                }
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "if(.@Status != 0)")
    }

    @Test
    fun `multiply and divide should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val price = variable("Price", 100)
                val tax = variable("Tax", 0)
                assign(tax, price * 2)
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, ".@Tax = .@Price * 2;")
    }

    @Test
    fun `modulo should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val value = variable("Value", 10)
                val remainder = variable("Remainder", 0)
                assign(remainder, value % 3)
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, ".@Remainder = .@Value % 3;")
    }

    @Test
    fun `gte and lte should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                val level = variable("Level", 0)
                `if`(level gte 10) {
                    message("High enough!")
                }
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "if(.@Level >= 10)")
    }

    @Test
    fun `int literal in if condition should synthesize correctly`() {
        val npc =
            npc(name = "Test", sprite = 100) {
                `if`(Zeny eq 0) {
                    message("You're broke!")
                }
            }
        val output = npc.synthesize(synthesizer)
        assertContains(output, "if(Zeny == 0)")
    }
}

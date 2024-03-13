package de.theoptik.rathenakt.models

import java.lang.System.lineSeparator

class IfCondition(private val statement: Statement) : Scope() {

    override fun synthesize(): String {
        return listOf("if(${statement.synthesize()}){", super.synthesize(), "}").joinToString(lineSeparator())
    }
}

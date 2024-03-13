package de.theoptik.rathenakt.models

import java.io.File

interface Synthesizable {
    fun synthesize(): String

    fun synthesize(fileName: String) {
        File(fileName).writeText(synthesize())
    }
}

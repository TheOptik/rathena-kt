package de.theoptik.rathenakt.models

import java.io.File

interface Synthesizable {
    //TODO: migrate to visitor pattern
    fun synthesize(): String

    fun synthesize(fileName: String) {
        File(fileName).writeText(synthesize())
    }
}

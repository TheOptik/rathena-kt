package de.theoptik.rathenakt.models

import de.theoptik.rathenakt.Synthesizer

interface Synthesizable {
    fun synthesize(synthesizer: Synthesizer): String
}

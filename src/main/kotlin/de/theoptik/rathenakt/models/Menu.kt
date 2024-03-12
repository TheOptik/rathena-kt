package de.theoptik.rathenakt.models

class Menu {
    val options: MutableMap<String,Scope?> = mutableMapOf()
    fun option(optionText:String, scope: Scope?){
        options[optionText] = scope
    }
}
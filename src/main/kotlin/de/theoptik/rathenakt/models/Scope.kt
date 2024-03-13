package de.theoptik.rathenakt.models

open class Scope(open val name: String?) : Synthesizable {
    private val parts: MutableList<ScopePart> = mutableListOf()

    operator fun invoke(init: Scope.()->Unit){
        this.init()
    }
    fun clearMessages() {
        parts.add(ScopePartClear)
    }

    fun addMessage(message: String) {
        parts.add(ScopePartMessage(message))
    }

    fun goto(scope: Scope) {
        parts.add(ScopePartGoto(scope))
    }

    fun menu(init: Menu.()->Unit){
        val menu = Menu()
        menu.init()
        parts.add(ScopePartMenu(menu.options))
    }

    override fun synthesize(): String {
        val partsWithEnd = if (!parts.isEmpty() && parts.last() !is ScopePartGoto) {
            parts + ScopePartClose
        } else {
            parts
        }
        return partsWithEnd.map { "\t${it.synthesize()};" + System.lineSeparator() }.joinToString("")
    }

    fun variable(name: String, initialValue:String): Variable<String> {
        val variable =ScopeStringVariable(name,initialValue)
        parts.add(ScopePartVaraibleInstantiation(variable))
        return variable
    }

    fun variable(name: String, initialValue:Int): Variable<Int> {
        val variable =ScopeIntVariable(name,initialValue)
        parts.add(ScopePartVaraibleInstantiation(variable))
        return variable
    }


}

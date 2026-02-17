# rathena-kt

A Kotlin DSL for writing [rAthena](https://github.com/rathena/rathena) NPC scripts with type safety, autocompletion, and compile-time error checking.

## Why?

Writing rAthena NPC scripts in raw text is error-prone:
- No type safety for variable prefixes (`@`, `.@`, `$`, `#`, etc.)
- No IDE support or autocompletion
- Easy to make syntax mistakes that only show up at runtime

**rathena-kt** lets you write NPC scripts in Kotlin using a clean DSL that compiles down to valid rAthena script syntax.

## Example

```kotlin
val testNpc = npc(
    name = "Test",
    sprite = 589,
    mapReferences = MapReferences.PRONTERA,
    coordinates = Coordinates(x = 156, y = 145, facingDirection = FacingDirection.SOUTH),
) {
    val hello = scope("L_Hello")

    val goodbye = scope("L_Goodbye") {
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
```

**Output:**
```
prontera,156,145,4	script	Test	589,{
	mes "What would you like to say to me?";
	menu "hello",L_Hello,"goodbye",L_Goodbye,"screw you",-;
L_Hello:
	clear;
	mes "Hello traveler";
	goto L_Goodbye;
	close;

L_Goodbye:
	clear;
	mes "Have a nice day!";

}
```

## Features

- **Type-safe variables** — correct rAthena prefixes generated automatically
  - Scope variables (`.@name` / `.@name$`)
  - Character variables (`@name` / `@name$`)
  - Permanent character variables (`name` / `name#`)
- **NPC definition** with map position, sprite, and facing direction
- **Dialog system** — `message()`, `clearMessages()`, `menu {}`, `select()`
- **Control flow** — `if` conditions, `goto`, `end`
- **Expressions** — comparisons, string concatenation, built-in functions
- **Built-in functions** — `gettimetick()`, `strcharinfo()`, `callfunc()`

## Building

Requires JDK 8+ and Maven.

```bash
mvn compile
mvn exec:java -Dexec.mainClass="de.theoptik.rathenakt.LauncherKt"
```

## Roadmap

See [ROADMAP.md](ROADMAP.md) for planned features and known issues.

## License

[MIT](LICENSE)

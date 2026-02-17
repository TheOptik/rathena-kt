# rathena-kt Roadmap

## 🐛 Bugs (Fix First)
- [x] `Variable.kt`: `lt` generates `GreaterThanStatement` instead of `LessThanStatement` — need new statement class
- [x] `Variable.kt`: `PermanentCharacterStringVariable` extends `Variable<Int>` — should be `Variable<String>`
- [x] `Synthesizer.kt`: `close` auto-insertion logic may be inverted — adds close after terminating parts instead of non-terminating
- [x] `Statement.kt`: Typo `ConcatinazedStatement` → `ConcatenatedStatement`

## 🔧 Core Missing Features (Priority)
- [ ] Arithmetic operators (`+`, `-`, `*`, `/`, `%`, `+=`, `-=`)
- [ ] Comparison operators (`==`, `!=`, `>=`, `<=`)
- [ ] Assignment operators for variables (`Zeny -= price`)
- [ ] Literal int/value statements (`IntStatement(2)`)
- [ ] `else` / `else if` support
- [ ] `callfunc` with multiple arguments (varargs)
- [ ] `select` as proper DSL (not raw string)
- [ ] Explicit `close()` function in Scope

## 📦 rAthena Commands to Add
- [ ] `specialeffect` / `specialeffect2`
- [ ] `sc_start` (status change)
- [ ] `percentheal`
- [ ] `set` (explicit variable assignment)
- [ ] `announce`
- [ ] `warp` / `areawarp`
- [ ] `getitem` / `delitem`
- [ ] `input` (player text/number input)
- [ ] `cutin` (NPC portrait)
- [ ] `sleep` / `sleep2`
- [ ] `next` (dialog page break)

## 🏗️ Architecture Improvements
- [ ] Indentation tracking in Synthesizer (nested if support)
- [ ] Consider making `Npc` constructor internal
- [ ] Add missing variable types (global `$`, instance `.`)
- [ ] Consistent string quoting in synthesizer output
- [ ] Add comment/documentation support in generated output
- [ ] Unit tests for synthesizer output

## 🎯 Milestone: Complete Healer Script
The commented-out section in `Launcher.kt` is the benchmark. When we can fully express the healer NPC in the DSL, the core is solid. Needs:
- [x] Variables and if conditions
- [ ] `select` with return value comparison (`== 2`)
- [ ] Arithmetic assignment (`Zeny -= price`)
- [ ] `specialeffect2`, `sc_start`, `percentheal`
- [ ] `end` after conditional blocks

## 📝 Nice-to-Haves (Later)
- [ ] Script validation (detect unreachable labels, missing ends)
- [ ] Import/read existing rAthena scripts → DSL (reverse transpiler)
- [ ] Multi-file output support
- [ ] README.md for the project
- [ ] Publish as library (Maven Central / GitHub Packages)

---
*Last updated: 2026-02-16 by Big Cheese 🧀*

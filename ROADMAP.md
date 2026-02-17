# rathena-kt Roadmap

## 🐛 Bugs (Fix First)
- [x] `Variable.kt`: `lt` generates `GreaterThanStatement` instead of `LessThanStatement` — PR #11
- [x] `Variable.kt`: `PermanentCharacterStringVariable` extends `Variable<Int>` — PR #10
- [x] `Synthesizer.kt`: `close` auto-insertion logic inverted — PR #12
- [x] `Statement.kt`: Typo `ConcatinazedStatement` → `ConcatenatedStatement` — PR #9

## ✅ Completed Features
- [x] Arithmetic operators (`+`, `-`, `*`, `/`, `%`) — PR #14
- [x] Comparison operators (`==`, `!=`, `>=`, `<=`, `>`, `<`) — PR #14
- [x] Assignment operators (`assign`, `plusAssign`, `minusAssign`) — PR #14
- [x] Literal int/value statements (`IntLiteralStatement`) — PR #14
- [x] `Int` overloads for all comparison/arithmetic operators — PR #14
- [x] ktlint formatting + Docker-based format script — PR #13
- [x] Unit tests (15 tests passing: 6 close logic + 9 arithmetic)
- [x] README.md — pushed directly
- [x] Variables and if conditions — original implementation

## 🔧 Core Missing Features (Priority)
- [ ] `else` / `else if` support
- [ ] `callfunc` with multiple arguments (varargs)
- [ ] `select` as proper DSL (not raw string)
- [ ] Explicit `close()` function in Scope

## 🔧 Follow-ups from PR #14 Review
- [ ] Reduce duplication between `Variable` and `Statement` operators (~120 lines)
- [ ] Replace raw `String` in `ScopePartCompoundAssignment` with enum
- [ ] Add missing compound assignments (`timesAssign`, `divAssign`, `remAssign`)
- [ ] Add parentheses support for nested expressions
- [ ] Remove unused `assertFalse` import in `ArithmeticTest.kt`

## 📦 rAthena Commands to Add
- [ ] `specialeffect` / `specialeffect2`
- [ ] `sc_start` (status change)
- [ ] `percentheal`
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

## 🎯 Milestone: Complete Healer Script
The commented-out section in `Launcher.kt` is the benchmark. When we can fully express the healer NPC in the DSL, the core is solid. Needs:
- [x] Variables and if conditions
- [x] Arithmetic assignment (`Zeny -= price`)
- [x] Comparison operators (`== 2`)
- [ ] `select` with return value comparison
- [ ] `specialeffect2`, `sc_start`, `percentheal`
- [ ] `end` after conditional blocks — ✅ partially done (last `if(delay)` block un-commented)

## 📝 Nice-to-Haves (Later)
- [ ] Script validation (detect unreachable labels, missing ends)
- [ ] Import/read existing rAthena scripts → DSL (reverse transpiler)
- [ ] Multi-file output support
- [ ] Publish as library (Maven Central / GitHub Packages)

---
*Last updated: 2026-02-17 by Big Cheese 🧀*

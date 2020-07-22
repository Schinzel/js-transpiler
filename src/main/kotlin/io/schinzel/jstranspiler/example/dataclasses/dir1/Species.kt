package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript

@Suppress("unused")
@JsTranspiler_CompileToJavaScript
enum class Species(val lifeSpan: Int, val alignment: String) {
    CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
}

package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.basic_utils_kotlin.println
import io.schinzel.jstranspiler.example.misc.Serialization
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript

@JsTranspiler_CompileToJavaScript
enum class Species(val lifeSpan: Int, val alignment: String) {
    CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
}

fun main() {
    Serialization
            .objectToJsonString(Species.CAT)
            .println()

}
package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript

@JsTranspiler_CompileToJavaScript
enum class Species(val age: Int) {
    CAT(13), DOG(2)
}
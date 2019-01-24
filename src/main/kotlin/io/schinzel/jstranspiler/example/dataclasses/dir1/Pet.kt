package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CreateSetter

@JsTranspiler_CompileToJavaScript
data class Pet (
        @JsTranspiler_CreateSetter val name: String,
        @JsTranspiler_CreateSetter val species: Species)
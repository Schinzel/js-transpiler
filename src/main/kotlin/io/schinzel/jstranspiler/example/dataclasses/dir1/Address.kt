package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript

@JsTranspiler_CompileToJavaScript
data class Address(
        val streetAddress: String,
        val zip: String,
        val city: String,
        val country: String
)
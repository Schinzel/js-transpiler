package io.schinzel.jstranspiler.transpiler.util.dir2

import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript

@JsTranspiler_CompileToJavaScript
data class KotlinDataClass(
    val accountNumber: String,
    val balance: Int
)
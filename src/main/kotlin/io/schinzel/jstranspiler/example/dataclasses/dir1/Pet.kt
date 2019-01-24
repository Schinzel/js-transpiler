package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.transpiler.JsTranspilerSetter

data class Pet (
        @JsTranspilerSetter val name: String,
        @JsTranspilerSetter val species: Species)
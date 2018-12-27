package io.schinzel.jstranspiler.example.dataclasses.dir1

import io.schinzel.jstranspiler.annotation.JsTranspilerSetter

/**
 * Purpose of this class is ...
 */
data class Pet (
        @JsTranspilerSetter val name: String,
        @JsTranspilerSetter val species: Species)
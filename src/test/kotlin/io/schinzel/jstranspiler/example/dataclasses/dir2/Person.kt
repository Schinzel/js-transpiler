package io.schinzel.jstranspiler.example.dataclasses.dir2

import com.fasterxml.jackson.annotation.JsonIgnore
import io.schinzel.jstranspiler.example.dataclasses.dir1.Address
import io.schinzel.jstranspiler.example.dataclasses.dir1.Pet
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CreateSetter
import java.time.Instant

@JsTranspiler_CompileToJavaScript
data class Person(
        @JsTranspiler_CreateSetter
        val firstName: String,
        @JsTranspiler_CreateSetter
        val lastName: String,
        val age: Int,
        val income: Long,
        val heightInMeter: Float,
        val healthy: Boolean,
        val homeAddress: Address,
        @JsTranspiler_CreateSetter
        val nicknames: List<String>,
        @JsTranspiler_CreateSetter
        val luckyNumbers: List<Int>,
        @JsTranspiler_CreateSetter
        val pets: List<Pet>,
        @JsTranspiler_CreateSetter
        val traits: List<Trait>,
        @JsTranspiler_CreateSetter
        val lastEdited: Instant,
        @JsonIgnore
        val iq: Int = 100)
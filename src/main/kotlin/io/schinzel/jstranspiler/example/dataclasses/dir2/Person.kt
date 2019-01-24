package io.schinzel.jstranspiler.example.dataclasses.dir2

import io.schinzel.jstranspiler.example.dataclasses.dir1.Address
import io.schinzel.jstranspiler.example.dataclasses.dir1.Pet
import io.schinzel.jstranspiler.transpiler.JsTranspilerSetter

/**
 * Purpose of this class is ...
 */
data class Person(
        @JsTranspilerSetter val firstName: String,
        @JsTranspilerSetter val lastName: String,
        val age: Int,
        val income: Long,
        val heightInMeter: Float,
        val healthy: Boolean,
        val homeAddress: Address,
        @JsTranspilerSetter val nicknames: List<String>,
        @JsTranspilerSetter val luckyNumbers: List<Int>,
        @JsTranspilerSetter val pets: List<Pet>)
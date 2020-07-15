package io.schinzel.jstranspiler.example.dataclasses.dir1


/**
 * Since this class does not have the annotation instruction to compile
 * to JavaScript, it will not be compiled to JavaScript.
 */
@Suppress("unused")
class Car(val brand: String, val year: Int)
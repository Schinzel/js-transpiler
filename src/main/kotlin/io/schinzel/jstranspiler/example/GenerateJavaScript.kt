package io.schinzel.jstranspiler.example

import io.schinzel.jstranspiler.JsTranspilerBuilder
import io.schinzel.jstranspiler.println

/**
 * Purpose of this file is to provide sample code for who to use this project to
 * generate JavaScript data files from Kotlin data classes
 */
fun main() {
    generateJavaScriptClasses()
}

fun generateJavaScriptClasses() {
    val baseDir = "io.schinzel.jstranspiler.example.dataclasses"
    JsTranspilerBuilder()
            .addSourcePackage("$baseDir.dir1")
            .addSourcePackage("$baseDir.dir2")
            .setDestinationFile("src/main/resources/mysite/js/classes.js")
            .buildAndRun()
    "JavaScript code generated".println()
}
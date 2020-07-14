package io.schinzel.jstranspiler.example

import io.schinzel.jstranspiler.JsTranspiler


/**
 * Purpose of this file is to provide sample code for who to use this project to
 * generate JavaScript data files from Kotlin data classes
 */
fun main() {
    generateJavaScriptClasses()
}

fun generateJavaScriptClasses() {
    JsTranspiler(
            sourcePackageName = "io.schinzel.jstranspiler.example",
            destinationFile = "src/main/resources/mysite/js/classes.js"
    )
}
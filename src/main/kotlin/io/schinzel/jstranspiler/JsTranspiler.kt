package io.schinzel.jstranspiler

import io.schinzel.basic_utils_kotlin.println
import io.schinzel.jstranspiler.transpiler.KotlinPackage
import java.io.File

/**
 * The main class for this project. Generates JavaScript code from the argument list of packages
 * and writes them to the argument file.
 *
 * @param sourcePackageNames A list of names of Kotlin packages in which to look for Kotlin code
 * to be transpiled to JavaScript.
 * @param destinationFile The name of the file into which the generated JavaScript will be written.
 * E.g. "src/main/resources/my_site/js/classes.js"
 */
class JsTranspiler(sourcePackageNames: List<String>, destinationFile: String) {

    constructor(sourcePackageName: String, destinationFile: String) : this(listOf(sourcePackageName), destinationFile)

    init {
        val startExecutionTime = System.nanoTime()
        // Check so that argument destination file name is ok
        validateFile(destinationFile)
        val kotlinPackage = KotlinPackage(sourcePackageNames)
        // Transpile all argument packages to JavaScript
        val javaScriptCode: String = kotlinPackage.toJavaScript()
        // File content is file header plus generated JavaScript code
        val jsFileContent: String = fileHeader + dataObjectClass + javaScriptCode
        // Write generated header and JavaScript to the argument file
        File(destinationFile).writeText(jsFileContent)
        // Calc execution time
        val jobExecutionTimeInSeconds = (System.nanoTime() - startExecutionTime) / 1_000_000_000
        val feedback = "JsTranspiler ran! Produced ${kotlinPackage.numberOfClassesAndEnums} JavaScript " +
                "classes and enums from Kotlin in $jobExecutionTimeInSeconds seconds."
        feedback.println()
    }


    companion object {
        private fun validateFile(fileName: String) {
            if (!fileName.endsWith(".js")) {
                throw RuntimeException("Destination file must have the extension js")
            }
        }

        /**
         * The header of the generated JavaScript file
         */
        private val fileHeader = """
            |/**
            | * This is an automatically generated file.
            | * Kotlin data classes have been translated into JavaScript classes.
            | */
            |
        """.trimMargin()

        /**
         * The header of the generated JavaScript file
         */
        val dataObjectClass = """
            | // noinspection JSUnusedLocalSymbols
            |/**
            | * This class holds methods common to all transpiled classes.
            | */
            |class DataObject {
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {object} This instance as a json object
            |     */
            |    asJsonObject() {
            |        return JSON.parse(JSON.stringify(this));
            |    }
            |
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {string} This instance as a json string
            |     */
            |    asJsonString() {
            |        return JSON.stringify(this);
            |    }
            |
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {object} A clone of this object
            |     */
            |    clone() {
            |        return new this.constructor(this.asJsonObject());
            |    }
            |}
            |
            |
        """.trimMargin()

    }

}






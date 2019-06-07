package io.schinzel.jstranspiler

import io.schinzel.jstranspiler.transpiler.KotlinPackage
import io.schinzel.jstranspiler.transpiler.compileToJs
import java.io.File

/**
 * The main class for this project. Generates JavaScript code from the argument list of packages
 * and writes them to the argument file.
 *
 * @param destinationFile The name of the file into which the generated JavaScript will be written.
 * E.g. "src/main/resources/mysite/js/classes.js"
 * @param listOfPackagePathAndNames A list of names of Kotlin packages in which to read Kotlin code
 * to be transpiled to JavaScript.
 */
class JsTranspiler(destinationFile: String, listOfPackagePathAndNames: List<String>) {

    init {
        //Transpile all argument packages to JavaScript
        val javaScriptCode: String = listOfPackagePathAndNames
                .map { KotlinPackage(it) }
                .compileToJs()
        //File content is file header plus generated JavaScript code
        val jsFileContent: String = fileHeader
                .plus(javaScriptCode)
        //Write generated header and JavaScript to the argument file
        File(destinationFile).writeText(jsFileContent)
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
            |/**
            | * This class holds methods common to all transpiled classes.
            | */
            |export class DataObject {
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


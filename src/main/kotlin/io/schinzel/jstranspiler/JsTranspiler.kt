package io.schinzel.jstranspiler

import io.schinzel.jstranspiler.transpiler.Package
import io.schinzel.jstranspiler.transpiler.compileToJs
import java.io.File

/**
 * The main class for this project.
 * @param destinationFile The name of the file into which the generated JavaScript will be written. E.g. "src/main/resources/mysite/js/classes.js"
 * @param listOfPackagePathAndNames A list of names of Kotlin packages in which to read Kotlin code to be transpiled to JavaScript.
 */
class JsTranspiler(destinationFile: String, listOfPackagePathAndNames: List<String>) {

    init {
        val javaScriptCode: String = listOfPackagePathAndNames
                .map { Package(it) }
                .compileToJs()
        val jsFileContent = JsTranspiler
                .getFileHeader()
                .plus(javaScriptCode)
        File(destinationFile).writeText(jsFileContent)
    }


    companion object {
        private fun getFileHeader() = """
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
            |}
            |
            |
        """.trimMargin()
    }

}
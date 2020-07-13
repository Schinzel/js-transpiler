package io.schinzel.jstranspiler


/**
 * The purpose of this class is to be a builder for the main class in this project. This is
 * the class that should be used to compile Kotlin to JavaScript code.
 */
class JsTranspilerBuilder {
    //Where the generated JavaScript file should be written to
    private lateinit var destinationFile: String
    //Package in which to look for data classes to convert to JavaScript
    private lateinit var packageName: String

    companion object {
        private fun validateFile(fileName: String) {
            if (!fileName.contains(".")) {
                throw RuntimeException("Missing dot in destination file name")
            }
            if (!fileName.endsWith(".js")) {
                throw RuntimeException("Destination file must have the extension js")
            }
        }
    }

    /**
     * Sets the file into which the generated JavaScript files will be
     * written
     * @param destinationFile
     * @return This for chaining
     */
    fun setDestinationFile(destinationFile: String): JsTranspilerBuilder {
        validateFile(destinationFile)
        this.destinationFile = destinationFile
        return this
    }

    /**
     * Add a package in which to look for data classes to convert to JavaScript
     * @param sourcePackage
     * @return This for chaining
     */
    fun setSourcePackage(sourcePackage: String): JsTranspilerBuilder {
        this.packageName = sourcePackage
        return this
    }


    /**
     * Find all data classes in added packages and generate
     * equivalent JavaScript classes
     */
    fun buildAndRun() {
        JsTranspiler(destinationFile, packageName)
    }
}
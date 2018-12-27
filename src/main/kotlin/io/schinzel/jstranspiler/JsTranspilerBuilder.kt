package io.schinzel.jstranspiler


/**
 * The purpose of this class is to be a builder for the main class in this project. This is
 * the class that should be used to compile Kotlin to JavaScript code.
 */
class JsTranspilerBuilder {
    //Where the generated JavaScript file should be written to
    private lateinit var destinationFile: String
    //Packages in which to look for data classes to convert to JavaScript
    private val listOfPackagePathAndNames = mutableListOf<String>()

    companion object {
        private fun validateFile(fileName: String) {
            if (!fileName.contains(".")) {
                throw RuntimeException("Missing dot in destination file name")
            }
            if (fileName.substringAfterLast(".") != "js") {
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
        JsTranspilerBuilder.validateFile(destinationFile)
        this.destinationFile = destinationFile
        return this
    }

    /**
     * Add a package in which to look for data classes to convert to JavaScript
     * @param sourcePackage
     * @return This for chaining
     */
    fun addSourcePackage(sourcePackage: String): JsTranspilerBuilder {
        listOfPackagePathAndNames.add(sourcePackage)
        return this
    }


    /**
     * Find all data classes in added packages and generate
     * equivalent JavaScript classes
     */
    fun buildAndRun() {
        JsTranspiler(destinationFile, listOfPackagePathAndNames)
    }
}
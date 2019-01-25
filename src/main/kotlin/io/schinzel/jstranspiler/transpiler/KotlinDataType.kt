package io.schinzel.jstranspiler.transpiler

/**
 * Purpose of this class is to represent a Kotlin data type
 */
internal open class KotlinDataType(val fullName: String) {
    val isList: Boolean = fullName
            .startsWith("kotlin.collections.List")
    val isListOfPrimitiveDataType: Boolean =
            this.isList && fullName
                    .substringAfter("<")
                    .startsWith("kotlin")
    val className = fullName.substringAfterLast(".")
    /**
     * Examples:
     *  kotlin.collections.List<io.schinzel.js-transpiler.example.dataclasses.dir1.Pet> -> Pet
     *  kotlin.collections.List<kotlin.String> -> String
     */
    val listDataTypeName: String = fullName
            .substringAfterLast(".")
            .substringBefore(">")


}

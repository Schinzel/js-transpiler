package io.schinzel.jstranspiler.transpiler

/**
 * Purpose of this class is to represent a Kotlin data type
 */
internal open class KotlinDataType(val kotlinDataTypeName: String) {
    val isList: Boolean = kotlinDataTypeName
            .startsWith("kotlin.collections.List")
    val isListOfPrimitiveDataType: Boolean =
            this.isList && kotlinDataTypeName
                    .substringAfter("<")
                    .startsWith("kotlin")
    /**
     * Example:
     *  kotlin.collections.List<io.schinzel.dataclasstojs.example.dataclasses.dir1.Pet> -> Pet
     *  kotlin.collections.List<kotlin.String> -> String
     */
    val listDataTypeName: String = kotlinDataTypeName
            .substringAfterLast(".")
            .substringBefore(">")


}

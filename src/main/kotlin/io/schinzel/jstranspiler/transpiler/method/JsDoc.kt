package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.KotlinDataType

/**
 * Purpose of this class is to return JSDoc data type for Kotlin data types
 */
internal class JsDoc {


    companion object {

        fun getDataTypeName(koltinDataType: KotlinDataType): String =
                when {
                    koltinDataType.isListOfPrimitiveDataType -> koltinDataType.listDataTypeName.toLowerCase() + "[]"
                    koltinDataType.isList -> koltinDataType.listDataTypeName + "[]"
                    else -> getNonListDataTypeName(koltinDataType)
                }


        private fun getNonListDataTypeName(kotlinDataType: KotlinDataType): String =
                when (kotlinDataType.kotlinDataTypeName) {
                    "kotlin.String" -> "string"
                    "kotlin.Long" -> "number"
                    "kotlin.Int" -> "number"
                    "kotlin.Float" -> "number"
                    "kotlin.Double" -> "number"
                    "kotlin.Boolean" -> "boolean"
                    else -> kotlinDataType.kotlinDataTypeName.substringAfterLast(".")
                }
    }
}
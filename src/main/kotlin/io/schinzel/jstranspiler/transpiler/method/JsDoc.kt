package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.KotlinDataType

/**
 * Purpose of this class is to return JSDoc data type for Kotlin data types
 */
internal class JsDoc {


    companion object {

        fun getDataTypeName(kotlinDataType: KotlinDataType): String =
                when {
                    kotlinDataType.isListOfPrimitiveDataType -> kotlinDataType.listDataTypeName.toLowerCase() + "[]"
                    kotlinDataType.isList -> kotlinDataType.listDataTypeName + "[]"
                    else -> getNonListDataTypeName(kotlinDataType)
                }


        private fun getNonListDataTypeName(kotlinDataType: KotlinDataType): String =
                when (kotlinDataType.fullName) {
                    "kotlin.String" -> "string"
                    "kotlin.Long" -> "number"
                    "kotlin.Int" -> "number"
                    "kotlin.Float" -> "number"
                    "kotlin.Double" -> "number"
                    "kotlin.Boolean" -> "boolean"
                    else -> kotlinDataType.className
                }
    }
}
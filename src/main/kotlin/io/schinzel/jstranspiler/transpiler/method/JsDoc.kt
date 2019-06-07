package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.*
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to return JSDoc data type for Kotlin data types
 */
internal class JsDoc {

    companion object {

        fun getDataTypeName(property: KProperty1<out Any, Any?>): String =
                when {
                    property.isListOfPrimitiveDataType() -> property.getListElementsSimpleClassName().toLowerCase() + "[]"
                    property.isList() -> property.getListElementsSimpleClassName() + "[]"
                    else -> getNonListDataTypeName(property)
                }


        private fun getNonListDataTypeName(property: KProperty1<out Any, Any?>): String =
                when (property.getFullClassName()) {
                    "kotlin.String" -> "string"
                    "kotlin.Long" -> "number"
                    "kotlin.Int" -> "number"
                    "kotlin.Float" -> "number"
                    "kotlin.Double" -> "number"
                    "kotlin.Boolean" -> "boolean"
                    "java.time.Instant" -> "Date"
                    else -> property.getSimpleClassName()
                }

    }
}
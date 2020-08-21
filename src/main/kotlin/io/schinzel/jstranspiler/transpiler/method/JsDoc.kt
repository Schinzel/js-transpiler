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
                with(property.getFullClassName()) {
                    when {
                        // Use "startsWith" to handle both "kotlin.Int" and "kotlin.Int!". The
                        // latter is the case with int and Integer from Java
                        startsWith("kotlin.String") -> "string"
                        startsWith("kotlin.Long") -> "number"
                        startsWith("kotlin.Int") -> "number"
                        startsWith("kotlin.Float") -> "number"
                        startsWith("kotlin.Double") -> "number"
                        startsWith("kotlin.Boolean") -> "boolean"
                        startsWith("java.time.Instant") -> "Date"
                        else -> property.getSimpleClassName()
                    }
                }

    }
}
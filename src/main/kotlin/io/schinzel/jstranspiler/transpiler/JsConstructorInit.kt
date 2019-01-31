package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to generate the JavaScript code for setting up variables in the constructor
 */
internal class JsConstructorInit(private val property: KProperty1<out Any, Any?>) : IToJavaScript {


    override fun toJavaScript(): String {
        val jsCodeCast: String = jsCodeCast(property)
        return """
        |            /**
        |             * @private
        |             */
        |            this.${property.name} = $jsCodeCast""".trimMargin()
    }

    companion object {
        private fun jsCodeCast(property: KProperty1<out Any, Any?>): String {
            if (property.isListOfPrimitiveDataType() || property.isEnum()) {
                return "json.${property.name};"
            }
            if (property.isList()) {
                val listDataType: String = property.getListElementsSimpleClassName()
                return if (property.isListOfEnums())
                    "json.${property.name}.map(x => $listDataType[x]);"
                else
                    "json.${property.name}.map(x => new $listDataType(x));"
            }
            return jsCodeCastNonList(property.name, property.getFullClassName())
        }


        private fun jsCodeCastNonList(propertyName: String, propertyDataTypeName: String): String {
            return when (propertyDataTypeName) {
                "kotlin.Long" -> "parseInt(json.$propertyName);"
                "kotlin.Int" -> "parseInt(json.$propertyName);"
                "kotlin.Float" -> "parseFloat(json.$propertyName);"
                "kotlin.Double" -> "parseFloat(json.$propertyName);"
                "kotlin.String" -> "json.$propertyName;"
                "kotlin.Boolean" -> "json.$propertyName;"
                else -> "new ${propertyDataTypeName.substringAfterLast(".")}(json.$propertyName);"
            }
        }
    }
}
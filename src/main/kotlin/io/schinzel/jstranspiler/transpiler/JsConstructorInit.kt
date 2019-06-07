package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to generate the JavaScript code for setting up variables in the
 * JavaScript constructor
 */
internal class JsConstructorInit(private val property: KProperty1<out Any, Any?>) : IToJavaScript {


    /**
     * @return JavaScript code for the constructor for the constructor argument property
     */
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
            //If property is a list of primitive data type (e.g. a list of Int) OR an enum
            if (property.isListOfPrimitiveDataType() || property.isEnum()) {
                return "json.${property.name};"
            }
            //If argument property is a list
            if (property.isList()) {
                //Get the simple class name of the list. E.g. "Pet" in the example
                val listDataType: String = property.getListElementsSimpleClassName()
                //If the argument property is a list of enums
                return if (property.isListOfEnums())
                    "json.${property.name}.map(x => $listDataType[x]);"
                else
                    "json.${property.name}.map(x => new $listDataType(x));"
            }
            //If got here, argument property is not a list
            return jsCodeCastNonList(property.name, property.getFullClassName())
        }


        /**
         * @param propertyName The name of the property. E.g. personAge
         * @param propertyDataTypeName The name of the the data type. E.g. "kotlin.Int"
         * @return JavaScript code for casting casting the value sent from the server to a
         * JavaScript value
         */
        private fun jsCodeCastNonList(propertyName: String, propertyDataTypeName: String): String {
            return when (propertyDataTypeName) {
                "kotlin.Long" -> "parseInt(json.$propertyName);"
                "kotlin.Int" -> "parseInt(json.$propertyName);"
                "kotlin.Float" -> "parseFloat(json.$propertyName);"
                "kotlin.Double" -> "parseFloat(json.$propertyName);"
                "kotlin.String" -> "json.$propertyName;"
                "kotlin.Boolean" -> "json.$propertyName;"
                "java.time.Instant" -> "new Date(json.$propertyName);"
                else -> "new ${propertyDataTypeName.substringAfterLast(".")}(json.$propertyName);"
            }
        }
    }
}
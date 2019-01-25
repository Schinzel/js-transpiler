package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to generate the JavaScript code for setting up variables in the constructor
 */
internal class JsConstructorInit(
        private val propertyName: String,
        private val propertyDataType: KotlinDataType,
        private val isPropertyEnum: Boolean) : IToJavaScript {

    constructor(property: KProperty1<out Any, Any?>)
            : this(property.name, property.getKotlinDataType(), property.isEnum())


    override fun toJavaScript(): String {
        val jsCodeCast: String = jsCodeCast(propertyName, propertyDataType, isPropertyEnum)
        return """
        |            /**
        |             * @private
        |             */
        |            this.$propertyName = $jsCodeCast""".trimMargin()
    }

    companion object {
        private fun jsCodeCast(propertyName: String, propertyDataType: KotlinDataType, isPropertyEnum: Boolean): String {
            if (propertyDataType.isListOfPrimitiveDataType || isPropertyEnum) {
                return "json.$propertyName;"
            }
            if (propertyDataType.isList) {
                val listDataType: String = propertyDataType.listDataTypeName
                return "json.$propertyName.map(x => new $listDataType(x));"
            }
            return jsCodeCastNonList(propertyName, propertyDataType.fullName)
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
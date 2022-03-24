package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.getSimpleClassName
import io.schinzel.jstranspiler.transpiler.isEnum
import io.schinzel.jstranspiler.transpiler.isList
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to construct the JavaScript code for a setter
 */
internal class JsSetter(private val property: KProperty1<out Any, Any?>, private val dataClassName: String) : IToJavaScript {


    override fun toJavaScript(): String {
        val jsCodeMethodName = JsMethodUtil.getMethodName("set", property.name)
        val jsDocArgumentDataType = JsDoc.getDataTypeName(property)
        val jsDocMethodDescription = jsDocMethodDescription(property.isList())
        val jsPropertySetter: String = jsPropertySetter(property)
        val kotlinOrDataTypeName: String = property.getSimpleClassName()
        val kotlinOrJavaName = property.name
        val jsPropertyName: String = JsMethodUtil.getJsPropertyName(property.name)
        val jsCodeSetterStatement = jsCodeSetterStatement(kotlinOrDataTypeName, jsPropertyName, jsPropertySetter)
        return """
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * $jsDocMethodDescription
            |     * @param {$jsDocArgumentDataType} $jsPropertyName
            |     * @return {$dataClassName}
            |     */
            |    $jsCodeMethodName($jsPropertyName) {
            |        this.$kotlinOrJavaName = $jsCodeSetterStatement;
            |        return this;
            |    }
            |    """.trimMargin()
    }


    companion object {
        private fun jsCodeSetterStatement(
            kotlinOrJavaName: String,
            jsPropertyName: String,
            jsPropertySetter: String
        ): String = when (kotlinOrJavaName) {
            "LocalDate" -> "$jsPropertyName.split('-').map((it) => {return parseInt(it, 10)})"
            else -> "$jsPropertyName$jsPropertySetter"
        }


        private fun jsDocMethodDescription(isList: Boolean) =
            if (isList)
                "Argument array is copied and set"
            else
                ""


        private fun jsPropertySetter(property: KProperty1<out Any, Any?>) =
            when {
                property.isList() -> ".slice()"
                property.isEnum() -> ".name"
                else -> ""
            }
    }
}
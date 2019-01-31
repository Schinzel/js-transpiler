package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.*
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to construct the JavaScript code for a setter
 */
internal class JsSetter(private val property: KProperty1<out Any, Any?>) : IToJavaScript {


    override fun toJavaScript(): String {
        val jsCodeMethodName = JsMethodUtil.methodName("set", property.name)
        val jsDocArgumentDataType = JsDoc.getDataTypeName(KotlinDataType(property.getFullClassName()))
        val jsDocMethodDescription = jsDocMethodDescription(property.isList())
        val jsCodeArrayCopyString = JsMethodUtil.arrayCopyString(property.isList())
        val propertyName = property.name
        return """
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * $jsDocMethodDescription
            |     * @param {$jsDocArgumentDataType} $propertyName
            |     * @return {${property.getSimpleClassName()}}
            |     */
            |    $jsCodeMethodName($propertyName) {
            |        this.$propertyName = $propertyName$jsCodeArrayCopyString;
            |        return this;
            |    }
            |    """.trimMargin()
    }


    companion object {
        fun jsDocMethodDescription(isList: Boolean) = if (isList) "Argument array is copied and set" else ""
    }
}
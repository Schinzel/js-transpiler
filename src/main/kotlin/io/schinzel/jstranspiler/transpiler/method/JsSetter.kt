package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.KotlinDataType
import io.schinzel.jstranspiler.transpiler.getKotlinDataType
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to construct the JavaScript code for a setter
 */
internal class JsSetter private constructor(
        private val className: String,
        private val propertyName: String,
        private val propertyDataType: KotlinDataType)
    : IToJavaScript {


    /**
     * @param className The name of the Kotlin class
     * @param property A Kotlin data class property
     */
    constructor(className: String, property: KProperty1<out Any, Any?>)
            : this(className, property.name, property.getKotlinDataType())


    override fun toJavaScript(): String {
        val jsCodeMethodName = JsMethodUtil.methodName("set", propertyName)
        val jsDocArgumentDataType = JsDoc.getDataTypeName(propertyDataType)
        val jsDocMethodDescription = jsDocMethodDescription(propertyDataType.isList)
        val jsCodeArrayCopyString = JsMethodUtil.arrayCopyString(propertyDataType.isList)
        return """
            |    /**
            |     * $jsDocMethodDescription
            |     * @param {$jsDocArgumentDataType} $propertyName
            |     * @return {$className}
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
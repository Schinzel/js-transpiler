package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.*
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to construct the JavaScript code for a getter
 */
internal class JsGetter private constructor(
        private val propertyName: String,
        private val propertyDataType: KotlinDataType,
        private val isEnum: Boolean)
    : IToJavaScript {

    /**
     * @param property A Kotlin data class property
     */
    constructor(property: KProperty1<out Any, Any?>)
            : this(property.name, property.getKotlinDataType(), property.isEnum())


    override fun toJavaScript(): String {
        val jsCodeMethodName: String = JsMethodUtil.methodName("get", propertyName)
        val jsDocReturnDataType: String = JsDoc.getDataTypeName(propertyDataType)
        val jsDocReturnDoc: String = jsDocReturn(propertyDataType.isList)
        val jsCodeReturnStatement: String = jsCodeReturnStatement(isEnum, propertyDataType.isList, propertyName, propertyDataType.className)
        return """
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * @return {$jsDocReturnDataType} $jsDocReturnDoc
            |     */
            |    $jsCodeMethodName() {
            |        return $jsCodeReturnStatement;
            |    }
            |    """.trimMargin()

    }


    companion object {

        /**
         * @return For the JsDoc comment after the data type for @return.
         */
        internal fun jsDocReturn(isList: Boolean): String = if (isList) "A copy of the array held" else ""


        /**
         * @return The js code that does the actual return for data. E.g. "this.lastName"
         */
        internal fun jsCodeReturnStatement(isEnum: Boolean, isList: Boolean, propertyName: String, dataTypeName: String): String {
            return if (isEnum) {
                //E.g. "Species[this.species]"
                "$dataTypeName[this.$propertyName]"
            } else {
                val arrayCopyString: String = JsMethodUtil.arrayCopyString(isList)
                //E.g. Either "this.lastName" or "this.pets.slice()"
                "this.$propertyName$arrayCopyString"
            }
        }

    }
}
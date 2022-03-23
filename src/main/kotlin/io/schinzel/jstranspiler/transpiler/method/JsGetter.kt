package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.getSimpleClassName
import io.schinzel.jstranspiler.transpiler.isEnum
import io.schinzel.jstranspiler.transpiler.isList
import kotlin.reflect.KProperty1

/**
 * Purpose of this class is to construct the JavaScript code for a getter
 */
internal class JsGetter(private val property: KProperty1<out Any, Any?>) : IToJavaScript {

    override fun toJavaScript(): String {
        val jsCodeMethodName: String = JsMethodUtil.getMethodName("get", property.name)
        val jsDocReturnDataType: String = JsDoc.getDataTypeName(property)
        val jsDocReturnDoc: String = jsDocReturn(property.isList())
        val javaOrKotlinPropertyName = property.name
        val jsCodeReturnStatement: String = jsCodeReturnStatement(
            isEnum = property.isEnum(),
            isList = property.isList(),
            javaOrKotlinPropertyName = javaOrKotlinPropertyName,
            dataTypeName = property.getSimpleClassName()
        )
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
        internal fun jsCodeReturnStatement(
            isEnum: Boolean,
            isList: Boolean,
            javaOrKotlinPropertyName: String,
            dataTypeName: String
        ): String = when {
            dataTypeName == "LocalDate" ->
                //E.g. new LocalDate(this.birthDay[0], this.birthDay[1], this.birthDay[2]);
                "new LocalDate(this.$javaOrKotlinPropertyName[0], this.$javaOrKotlinPropertyName[1], this.$javaOrKotlinPropertyName[2])"
            isEnum ->
                //E.g. "Species[this.species]"
                "$dataTypeName[this.$javaOrKotlinPropertyName]"
            else -> {
                val arrayCopyString: String = arrayCopyString(isList)
                //E.g. Either "this.lastName" or "this.pets.slice()"
                "this.$javaOrKotlinPropertyName$arrayCopyString"
            }
        }


        /**
         * @return JavaScript code for copying an array. If is not a list
         * then return empty string
         */
        private fun arrayCopyString(isList: Boolean): String =
            if (isList) ".slice()" else ""


    }
}
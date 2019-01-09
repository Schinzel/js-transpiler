package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.firstCharToUpperCase


internal class JsMethodUtil {

    companion object {
        /**
         * @return JavaScript code for copying an array. If is not a list
         * then return empty string
         */
        fun arrayCopyString(isList: Boolean): String =
                if (isList) ".slice()" else ""

        /**
         * @param methodPrefix "get" or "set"
         * @param propertyName E.g. "lastName
         * @return E.g. "getLastName"
         */
        fun methodName(methodPrefix: String, propertyName: String): String =
                methodPrefix + propertyName.firstCharToUpperCase()

    }
}

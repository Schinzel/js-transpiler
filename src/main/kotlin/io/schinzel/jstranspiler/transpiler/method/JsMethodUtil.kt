package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.firstCharToUpperCase


internal class JsMethodUtil {

    companion object {
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

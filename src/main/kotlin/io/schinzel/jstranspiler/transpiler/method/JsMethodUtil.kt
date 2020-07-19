package io.schinzel.jstranspiler.transpiler.method

import io.schinzel.jstranspiler.transpiler.firstCharToUpperCase


/**
 * The purpose of this class is to hold code the is used by both the JsGetter and JsSetter classes
 */
internal class JsMethodUtil {

    companion object {

        /**
         * @param methodPrefix "get" or "set"
         * @param propertyName E.g. "lastName
         * @return E.g. "getLastName"
         */
        fun methodName(methodPrefix: String, propertyName: String): String =
                methodPrefix + propertyName.firstCharToUpperCase()

    }
}

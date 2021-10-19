package io.schinzel.jstranspiler.transpiler.method

import com.google.common.base.CaseFormat
import io.schinzel.jstranspiler.transpiler.firstCharToUpperCase


/**
 * The purpose of this class is to hold code the is used by both the JsGetter and JsSetter classes
 */
internal class JsMethodUtil {

    companion object {

        /**
         * Converts a camel-case or a snake-case property to a camel-case getter or setter
         *
         * @param methodPrefix "get" or "set"
         * @param javaOrKotlinName E.g. "lastName" or "last_name"
         * @return E.g. "getLastName"
         */
        fun getMethodName(methodPrefix: String, javaOrKotlinName: String): String =
                methodPrefix + getJsPropertyName(javaOrKotlinName).firstCharToUpperCase()


        fun getJsPropertyName(javaOrKotlinName: String): String =
                // If this snake-case
                if (javaOrKotlinName.contains('_'))
                    CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, javaOrKotlinName)
                // else, camel-case assumed
                else
                    javaOrKotlinName


    }
}

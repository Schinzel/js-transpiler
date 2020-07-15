package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.println
import io.schinzel.jstranspiler.printlnWithPrefix
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

/**
 * Purpose of this class is to construct the JavaScript code for a Kotlin enum
 *
 * For example. The below Kotlin enum
 * enum class Species {
 *    CAT, DOG
 * }
 *
 * would be compiled to the below JavaScript:
 * export const Species = Object.freeze({
 *    CAT: 'CAT',
 *    DOG: 'DOG'
 * });
 * @property myClass The kotlin enum to transpile to JavaScript
 */
internal class KotlinEnum(private val myClass: KClass<out Any>) : IToJavaScript {

    override fun toJavaScript(): String {
        // Get the name of the kotlin enum class
        val enumName: String = myClass.simpleName ?: throw RuntimeException()

        if (myClass.declaredMemberProperties.isNotEmpty()) {


            val enumConstants = myClass.java.enumConstants
            for (enumConst in enumConstants) {
                "###".println()
                val clazz: Class<*> = enumConst.javaClass
                enumConst.printlnWithPrefix("enumConst")

                if (myClass.declaredMemberProperties.isNotEmpty()) {
                    for (prop in myClass.declaredMemberProperties) {
                        prop.name.printlnWithPrefix("Property name")
                        val method: Method = clazz
                                .getDeclaredMethod("get${prop.name.firstCharToUpperCase()}")
                        method.invoke(enumConst)
                                .printlnWithPrefix("Property value")
                    }
                }


            }
        }


        // Get all the values of the enum class
        val enumValues: String = myClass.java.enumConstants
                .joinToString(separator = ",\n") { "    $it: '$it'" }
        return """
            |export const $enumName = Object.freeze({
            |$enumValues
            |});
            |
            |""".trimMargin()
    }
}




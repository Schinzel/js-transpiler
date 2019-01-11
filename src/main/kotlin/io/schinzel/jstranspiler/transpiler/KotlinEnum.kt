package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KClass

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
 */
internal class KotlinEnum(private val myClass: KClass<out Any>) : IToJavaScript {

    override fun toJavaScript(): String {
        val enumName: String = myClass.simpleName ?: throw RuntimeException()
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



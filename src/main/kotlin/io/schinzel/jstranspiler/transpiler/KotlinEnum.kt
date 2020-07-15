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
        // for each value in the enum
        //for (enumConst in myClass.java.enumConstants) {

        val enumValues: List<String> = myClass.java.enumConstants.map { enumConst ->
            // If the enum has properties
            val enumValue = if (myClass.declaredMemberProperties.isNotEmpty()) {
                "###".println()
                val clazz: Class<*> = enumConst.javaClass
                enumConst.printlnWithPrefix("enumConst")
                // For each property in the enum
                val propertyNameValuePairs: String = myClass.declaredMemberProperties
                        .map { prop ->
                            val method: Method = clazz
                                    .getDeclaredMethod("get${prop.name.firstCharToUpperCase()}")
                            val propValue = method.invoke(enumConst)
                            "${prop.name}: '$propValue'"
                        }
                        .joinToString(separator = ", ")
                        .printlnWithPrefix("Name Val")
                "{$propertyNameValuePairs}"
            } else {
                "'$enumConst'"
            }
            "$enumConst: $enumValue".printlnWithPrefix("Experiment")
        }


        // Get all the values of the enum class
        val enumValuesString: String = enumValues
                .joinToString(separator = ",\n")

        // Get the name of the kotlin enum class
        val enumName: String = myClass.simpleName ?: throw RuntimeException()

        return """
            |export const $enumName = Object.freeze({
            |$enumValuesString
            |});
            |
            |""".trimMargin()
    }
}


/**

Om int så behövs inte fnuttar
Få in intendation
Måste man ha med name?

https://stackoverflow.com/questions/44447847/enums-in-javascript-with-es6

export const Species = Object.freeze({
CAT: {name: 'cat', alignment: 'Chaotic Evil', averageLifeSpan: '16'},
DOG: {name: 'dog', alignment: 'Neutral Good', averageLifeSpan: '13'}
});


Working versions of JS-enums:
export const Trait = Object.freeze({
HUMBLE: 'HUMBLE',
HONORABLE: 'HONORABLE',
DILIGENT: 'DILIGENT',
LOYAL: 'LOYAL',
KIND: 'KIND'
});


export const Species = Object.freeze({
CAT: 'CAT',
DOG: 'DOG'
});
 */
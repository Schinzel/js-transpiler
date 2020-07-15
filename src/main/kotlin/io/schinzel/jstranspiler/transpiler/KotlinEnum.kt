package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.println
import io.schinzel.jstranspiler.printlnWithPrefix
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

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
        //myClass.staticProperties.println()
        //myClass.typeParameters.println()
        myClass.declaredMemberProperties.printlnWithPrefix("Member props")
        myClass.declaredMemberProperties.firstOrNull()?.printlnWithPrefix("First")


        myClass.declaredMemberProperties.firstOrNull()?.javaField.printlnWithPrefix("java field")

        //https://stackoverflow.com/questions/24260011/get-value-of-enum-by-reflection


        // ==== HEMMA START
        // Int
        myClass.declaredMemberProperties.firstOrNull()?.getSimpleClassName().printlnWithPrefix("Simple name")
        // Age
        myClass.declaredMemberProperties.firstOrNull()?.name.printlnWithPrefix("Name")
        // ==== HEMMA SLUT

        // Nästa steg. Itterera över  myClass.declaredMemberProperties för att få ut Age osv

        if (enumName == "Species") {
            val enumConstants = myClass.java.enumConstants
            for (enumConst in enumConstants) {
                val clzz: Class<*> = enumConst.javaClass
                val method: Method = clzz.getDeclaredMethod("getAge")
                val invoke: Any = method.invoke(enumConst)
                invoke.printlnWithPrefix("Age")
                //val `val` = method.invoke(enumConst) as String
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




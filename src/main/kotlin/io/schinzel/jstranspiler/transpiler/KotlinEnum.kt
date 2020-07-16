package io.schinzel.jstranspiler.transpiler

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
                // For each property in the enum
                val propertyNameValuePairs: String = myClass.declaredMemberProperties
                        .map { prop ->
                            val method: Method = enumConst.javaClass
                                    .getDeclaredMethod("get${prop.name.firstCharToUpperCase()}")
                            val propValue = method.invoke(enumConst)
                            if (prop.getSimpleClassName() == "String") {
                                "${prop.name}: '$propValue'"
                            } else {
                                "${prop.name}: $propValue"
                            }
                        }
                        .joinToString(separator = ", ")
                "{name: '$enumConst', $propertyNameValuePairs}"
            } else {
                "'$enumConst'"
            }
            "    $enumConst: $enumValue"
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

Nästa steg:
- Testa att alltid sätta name oavsätt om har properties eller inte...

Måste man ha med name?

Kolla in @typedef

Om det är en Enum med properties så
1) lägg till en @typdef
2) setters görs med name (





/**
 * @typedef {{name: string, alignment: string, averageLifeSpan: number}}  Species
*/

// noinspection JSUnusedGlobalSymbols
/**
 *
 * @param  {Species}  theSpecies
 * @return {Pet}
*/
setTheSpecies(theSpecies) {
this.theSpecies = theSpecies.name;
return this;
}


/**
 *
 * @param  {{name: string, alignment: string, averageLifeSpan: number}}  theSpecies
 * @return {Pet}
*/
setTheSpecies(theSpecies) {
this.theSpecies = theSpecies.name;
return this;
}

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
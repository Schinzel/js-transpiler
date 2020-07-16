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
        val propertyNameTypeMap: List<NameType> = myClass.declaredMemberProperties.map { property ->
            val data = DataType.getDataType(property.getSimpleClassName())
            NameType(property.name, data)
        }

        val enumValueList: List<EnumValue> = myClass.java.enumConstants.map { enumValue ->
            // propertyName propertyValue map
            val nameValueList: List<NameValue> =
                    propertyNameTypeMap.map { propertyNameType ->
                        val method: Method = enumValue.javaClass
                                .getDeclaredMethod("get${propertyNameType.name.firstCharToUpperCase()}")
                        val propValue = method.invoke(enumValue).toString()
                        NameValue(propertyNameType.name, propValue, propertyNameType.type)
                    }
            EnumValue(enumValue.toString(), nameValueList.plus(NameValue("name", enumValue.toString(), DataType.Text)))
        }


        val enumValueListAsString = enumValueList.joinToString(",\n") { enumValue ->
            val enumValuesAsString: String = enumValue.nameValueList.joinToString { nameValue ->
                val value = if (nameValue.type == DataType.Text) "'${nameValue.value}'" else nameValue.value
                nameValue.name + ": " + value
            }
            val enumValueName = enumValue.name
            "    $enumValueName: {$enumValuesAsString}"
        }.printlnWithPrefix("dd")

        // Get the name of the kotlin enum class
        val enumName: String = myClass.simpleName ?: throw Exception()

        return """
            |export const $enumName = Object.freeze({
            |$enumValueListAsString
            |});
            |
            |""".trimMargin()
    }
}

private data class EnumValue(val name: String, val nameValueList: List<NameValue>)

private data class NameValue(val name: String, val value: String, val type: DataType)

private data class NameType(val name: String, val type: DataType)

enum class DataType(val kotlinName: String, val jsName: String) {
    Text("String", "string"),
    Number("Int", "number");

    companion object {
        fun getDataType(kotlinDataType: String): DataType =
                values().first { it.kotlinName == kotlinDataType }

    }
}

/**

Nästa steg:
- Testa att alltid sätta name oavsätt om har properties eller inte...
- Setters med .name =

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
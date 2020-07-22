package io.schinzel.jstranspiler.transpiler

import io.schinzel.basic_utils_kotlin.toList
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

/**
 * Purpose of this class is to construct the JavaScript code for a Kotlin enum
 *
 * Example. The below Kotlin enum:
 * enum class Species(val lifeSpan: Int, val alignment: String) {
 *   CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
 * }
 *
 * ... would be compiled to the below JavaScript:
 * /**
 * @typedef {{name: string, alignment: string, lifeSpan: number}} Species
 * */
 * export const Species = Object.freeze({
 *   CAT: {name: 'CAT', alignment: 'Chaotic Evil', lifeSpan: 16},
 *   DOG: {name: 'DOG', alignment: 'Neutral Good', lifeSpan: 13}
 * });
 *
 *
 * @property enumClass The kotlin enum to transpile to JavaScript
 */
internal class KotlinEnum(private val enumClass: KClass<out Any>) : IToJavaScript {

    override fun toJavaScript(): String {
        // Construct a list with all properties of the constructor argument class
        val propertyTypeList: List<PropertyType> = getListOfPropertyTypes(enumClass)

        // Construct a list with one element for each enum value. Each element holds
        // the name of the enum value and the properties and their values
        val enumValueList: List<EnumValue> = getListOfEnumValues(enumClass, propertyTypeList)

        // A list with the enum value of the constructor argument enum
        val jsCodeEnumValues: String = getJsCodeForEnumValues(enumValueList)

        // Get the name of the kotlin enum class
        val jsCodeEnumName: String = enumClass.simpleName ?: throw Exception()

        // For example: name: string, alignment: string
        val jsCodeTypeDefProperties: String = propertyTypeList.joinToString { propertyNameType ->
            val jsDataType = if (propertyNameType.type == "String") "string" else "number"
            propertyNameType.name + ": " + jsDataType
        }

        return """
            |/**
            | * @typedef {{$jsCodeTypeDefProperties}} $jsCodeEnumName
            | */
            |export const $jsCodeEnumName = Object.freeze({
            |$jsCodeEnumValues
            |});
            |
            |""".trimMargin()
    }

    companion object {
        /**
         * @return A list with the names and data types of the properties of the argument enum
         */
        private fun getListOfPropertyTypes(enumClass: KClass<out Any>): List<PropertyType> =
                // Add a "name" property first
                PropertyType("name", "String").toList() +
                        enumClass.declaredMemberProperties
                                .map { property ->
                                    PropertyType(property.name, property.getSimpleClassName())
                                }


        /**
         * @return A list of the enum-values for the argument enum. Each enum value holds the name
         * of the enum value and its properties
         */
        private fun getListOfEnumValues(enumClass: KClass<out Any>, propertyTypeList: List<PropertyType>): List<EnumValue> =
                enumClass.java.enumConstants.map { enumValue ->
                    // Get all the properties of this enum-value
                    val propertyList: List<Property> =
                            propertyTypeList.map { propertyNameType ->
                                // The property value as string. For example "16" or "Neutral Good"
                                val propertyValue: String = enumValue.javaClass.kotlin.memberProperties
                                        .first { it.name == propertyNameType.name }
                                        .get(enumValue)
                                        .toString()
                                Property(propertyNameType.name, propertyValue, propertyNameType.type)
                            }
                    EnumValue(enumValue.toString(), propertyList)
                }


        /**
         * @return JavaScript code for the enum values. For example:
         *   CAT: {name: 'CAT', alignment: 'Chaotic Evil', lifeSpan: 16},
         *   DOG: {name: 'DOG', alignment: 'Neutral Good', lifeSpan: 13}
         *
         */
        private fun getJsCodeForEnumValues(enumValueList: List<EnumValue>): String =
                enumValueList.joinToString(",\n") { enumValue ->
                    // For example: name: 'DOG', alignment: 'Neutral Good'
                    val propertiesAsString: String = enumValue.propertyList.joinToString { nameValue ->
                        val propertyName = nameValue.name
                        val propertyValue = if (nameValue.type == "String") "'${nameValue.value}'" else nameValue.value
                        "$propertyName: $propertyValue"
                    }
                    val enumValueName = enumValue.name
                    // For example: DOG: {name: 'DOG', alignment: 'Neutral Good'}
                    "    $enumValueName: {$propertiesAsString}"
                }


    }
}


private data class PropertyType(val name: String, val type: String)

private data class EnumValue(val name: String, val propertyList: List<Property>)

private data class Property(val name: String, val value: String, val type: String)



/**

Nästa steg:
- tester
- refac

-nytt verre nr
- readme
- pr
- deploy

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
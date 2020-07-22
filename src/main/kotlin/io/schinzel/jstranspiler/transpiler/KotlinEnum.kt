package io.schinzel.jstranspiler.transpiler

import io.schinzel.basic_utils_kotlin.toList
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

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
 *   CAT: {name: 'CAT', lifeSpan: 16, alignment: 'Chaotic Evil'},
 *   DOG: {name: 'DOG', lifeSpan: 13, alignment: 'Neutral Good'}
 * });
 *
 *
 * @property enumClass The kotlin enum to transpile to JavaScript
 */
internal class KotlinEnum(private val enumClass: KClass<out Any>) : IToJavaScript {

    override fun toJavaScript(): String {
        // Construct a list with all properties of the constructor argument class
        val propertyTypeList: List<PropertyType> = getListOfPropertyTypes(enumClass)

        // Construct a list with one element for each enum value (i.e CAT, DOG).
        // Each element in the list holds a list of properties and their values
        val listOfListProperties: List<List<Property>> = getEnumValueProperties(enumClass, propertyTypeList)

        // A list with the enum value of the constructor argument enum
        val jsCodeEnumValues: String = getJsCodeForEnumValues(listOfListProperties)

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
         * For example name: String, lifeSpan: Int,  alignment: String
         */
        internal fun getListOfPropertyTypes(enumClass: KClass<out Any>): List<PropertyType> {
            val enumProperties: List<PropertyType> = enumClass.primaryConstructor?.parameters?.map {
                val name = it.name ?: throw Exception()
                val type = it.type.toString().substringAfter(".")
                PropertyType(name, type)
            } ?: emptyList()
            // Add a "name" property first
            return PropertyType("name", "String").toList() + enumProperties
        }


        /**
         * @return A list of the enum-values for the argument enum. Each enum value holds the name
         * of the enum value and its properties.
         * E.g.
         * [
         *   [
         *   Property(value=CAT, type=PropertyType(name=name, type=String)),
         *   Property(value=16, type=PropertyType(name=lifeSpan, type=Int)),
         *   Property(value=Chaotic Evil, type=PropertyType(name=alignment, type=String))
         *   ],
         *   [
         *   Property(value=DOG, type=PropertyType(name=name, type=String)),
         *   Property(value=13, type=PropertyType(name=lifeSpan, type=Int)),
         *   Property(value=Neutral Good, type=PropertyType(name=alignment, type=String))
         *   ]
         * ]
         */
        internal fun getEnumValueProperties(enumClass: KClass<out Any>, propertyTypeList: List<PropertyType>): List<List<Property>> =
                enumClass.java.enumConstants.map { enumValue ->
                    // Get all the properties of this enum-value
                    propertyTypeList.map { propertyNameType ->
                        // The property value as string. For example "16" or "Neutral Good"
                        val propertyValue: String = enumValue.javaClass.kotlin.memberProperties
                                .first { it.name == propertyNameType.name }
                                // Set as accessible to access properties of private enums
                                .also { it.isAccessible = true }
                                .get(enumValue)
                                .toString()
                        Property(propertyValue, propertyNameType)
                    }
                }


        /**
         * @return JavaScript code for the enum values. For example:
         *   CAT: {name: 'CAT', alignment: 'Chaotic Evil', lifeSpan: 16},
         *   DOG: {name: 'DOG', alignment: 'Neutral Good', lifeSpan: 13}
         *
         */
        internal fun getJsCodeForEnumValues(listOfListProperties: List<List<Property>>): String =
                listOfListProperties.joinToString(",\n") { listProperties ->
                    // For example: name: 'DOG', alignment: 'Neutral Good'
                    val propertiesAsString: String = listProperties.joinToString { property ->
                        val propertyName = property.type.name
                        val propertyValue = if (property.type.type == "String") "'${property.value}'" else property.value
                        "$propertyName: $propertyValue"
                    }
                    // The value of the enum is the first value
                    val enumValueName = listProperties[0].value
                    // For example: DOG: {name: 'DOG', alignment: 'Neutral Good'}
                    "    $enumValueName: {$propertiesAsString}"
                }


    }
}


internal data class Property(val value: String, val type: PropertyType)

internal data class PropertyType(val name: String, val type: String)


/**

Nästa steg:
- tester
- refac

- nytt verre nr
- readme
-- Sätt datum
- pr
- deploy


 */
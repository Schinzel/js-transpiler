package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.printlnWithPrefix
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

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
        // Construct a list with all properties of the constructor argument class
        val propertyNameTypeList: List<NameType> = listOf(NameType("name", DataType.Text)) +
                myClass.declaredMemberProperties
                        .map { property ->
                            val dataType = DataType.getDataType(property.getSimpleClassName())
                            NameType(property.name, dataType)
                        }

        // Construct a list with one element for each enum value. Each element holds
        // the name of the enum value and the properties and their values
        val enumValueList: List<EnumValue> = myClass.java.enumConstants.map { enumValue ->
            val propertyList: List<Property> =
                    propertyNameTypeList.map { propertyNameType ->
                        val propertyValue: String = enumValue.javaClass.kotlin.memberProperties
                                .first { it.name == propertyNameType.name }
                                .get(enumValue)
                                .toString()
                        Property(propertyNameType.name, propertyValue, propertyNameType.type)
                    }
            EnumValue(enumValue.toString(), propertyList)
        }

        // A list with the enum value of the constructor argument enum
        val enumValueListAsString: String = enumValueList.joinToString(",\n") { enumValue ->
            // For example: name: 'DOG', alignment: 'Neutral Good'
            val propertiesAsString: String = enumValue.propertyList.joinToString { nameValue ->
                val propertyName = nameValue.name
                val propertyValue = if (nameValue.type == DataType.Text) "'${nameValue.value}'" else nameValue.value
                "$propertyName: $propertyValue"
            }
            val enumValueName = enumValue.name
            // For example: DOG: {name: 'DOG', alignment: 'Neutral Good'}
            "    $enumValueName: {$propertiesAsString}"
        }

        // Get the name of the kotlin enum class
        val enumName: String = myClass.simpleName ?: throw Exception()

        val typeDefProperties = propertyNameTypeList.joinToString { propertyNameType ->
            propertyNameType.name + ": " + propertyNameType.type.jsDocName
        }

        val jsTypeDef = "{{$typeDefProperties}} $enumName"
        jsTypeDef.printlnWithPrefix("jsTypeDef")

        return """
            |/**
            | * @typedef $jsTypeDef
            | */
            |export const $enumName = Object.freeze({
            |$enumValueListAsString
            |});
            |
            |""".trimMargin()
    }
}

private data class EnumValue(val name: String, val propertyList: List<Property>)

private data class Property(val name: String, val value: String, val type: DataType)

private data class NameType(val name: String, val type: DataType)


enum class DataType(val kotlinName: String, val jsDocName: String) {
    Text("String", "string"),

    @Suppress("unused")
    Number("Int", "number");

    companion object {
        fun getDataType(kotlinDataType: String): DataType =
                values().first { it.kotlinName == kotlinDataType }
    }
}

/**

Nästa steg:
- In med type def
- Setters med .name =
- Lägg in printlnWithPrefix i kotlin-basic utils och använd det sin dependency

Kolla in @typedef

Om det är en Enum med properties så
2) setters görs med name

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
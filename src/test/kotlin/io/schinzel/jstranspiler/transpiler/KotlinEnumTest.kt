package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getListOfPropertyTypes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinEnumTest {

    @Suppress("unused")
    private enum class TestSpecies1(val lifeSpan: Int, val alignment: String) {
        CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
    }

    @Suppress("unused")
    private enum class Monsters {
        DRAGON, MINOTAUR
    }

    @Test
    fun `getListOfPropertyTypes-Size TestSpecies 3`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes.size).isEqualTo(3)
    }

    @Test
    fun `getListOfPropertyTypes-Size Monsters 1`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(Monsters::class.java.kotlin)
        assertThat(listOfPropertyTypes.size).isEqualTo(1)
    }

    @Test
    fun `getListOfPropertyTypesData-DataTypeFirst TestSpecies String`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[0].type).isEqualTo("String")
    }

    @Test
    fun `getListOfPropertyTypesData-DataTypeSecond TestSpecies Int`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[1].type).isEqualTo("Int")
    }

    @Test
    fun `getListOfPropertyTypesData-DataTypeThird TestSpecies String`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[2].type).isEqualTo("String")
    }

    @Test
    fun `getListOfPropertyTypesData-NameFirst TestSpecies name`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[0].name).isEqualTo("name")
    }

    @Test
    fun `getListOfPropertyTypesData-NameSecond TestSpecies lifeSpan`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[1].name).isEqualTo("lifeSpan")
    }

    @Test
    fun `getListOfPropertyTypesData-NameThird TestSpecies alignment`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies1::class.java.kotlin)
        assertThat(listOfPropertyTypes[2].name).isEqualTo("alignment")
    }




}
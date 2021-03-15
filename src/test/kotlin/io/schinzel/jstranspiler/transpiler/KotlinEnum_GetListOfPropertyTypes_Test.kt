package io.schinzel.jstranspiler.transpiler

import com.fasterxml.jackson.annotation.JsonIgnore
import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getListOfPropertyTypes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinEnum_GetListOfPropertyTypes_Test {

    @Suppress("unused")
    private enum class TestSpecies(
        val lifeSpan: Int,
        val alignment: String,
        @JsonIgnore
        val numberOfLegs: Int
    ) {
        CAT(16, "Chaotic Evil", 4), DOG(13, "Neutral Good", 4)
    }

    @Suppress("unused")
    private enum class Monsters {
        DRAGON, MINOTAUR
    }

    @Test
    fun `Size TestSpecies 3`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes.size).isEqualTo(3)
    }

    @Test
    fun `Size Monsters 1`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(Monsters::class.java.kotlin)
        assertThat(listOfPropertyTypes.size).isEqualTo(1)
    }

    @Test
    fun `DataTypeFirst TestSpecies String`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[0].kotlinDataType).isEqualTo("String")
    }

    @Test
    fun `DataTypeSecond TestSpecies Int`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[1].kotlinDataType).isEqualTo("Int")
    }

    @Test
    fun `DataTypeThird TestSpecies String`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[2].kotlinDataType).isEqualTo("String")
    }

    @Test
    fun `NameFirst TestSpecies name`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[0].name).isEqualTo("name")
    }

    @Test
    fun `NameSecond TestSpecies lifeSpan`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[1].name).isEqualTo("lifeSpan")
    }

    @Test
    fun `NameThird TestSpecies alignment`() {
        val listOfPropertyTypes: List<PropertyType> = getListOfPropertyTypes(TestSpecies::class.java.kotlin)
        assertThat(listOfPropertyTypes[2].name).isEqualTo("alignment")
    }


}
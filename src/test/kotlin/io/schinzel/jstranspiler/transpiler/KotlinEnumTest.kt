package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getListOfEnumValues
import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getListOfPropertyTypes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinEnum_getListOfEnumValues_Test {

    @Suppress("unused")
    private enum class TestSpecies(val lifeSpan: Int, val alignment: String) {
        CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
    }

    private val propertyTypes = getListOfPropertyTypes(TestSpecies::class.java.kotlin)


    @Test
    fun `FirstElementInListName TestSpecies CAT`() {
        val listOfEnumValues = getListOfEnumValues(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(listOfEnumValues[0].name).isEqualTo("CAT")
    }

    @Test
    fun `FirstElementInListAndFirstElementInPropertyListValue TestSpecies CAT`() {
        val listOfEnumValues = getListOfEnumValues(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(listOfEnumValues[0].propertyList[0].value).isEqualTo("CAT")
    }

    @Test
    fun `FirstElementInListAndSecondElementInPropertyListValue TestSpecies 16`() {
        val listOfEnumValues = getListOfEnumValues(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(listOfEnumValues[0].propertyList[1].value).isEqualTo("16")
    }
}